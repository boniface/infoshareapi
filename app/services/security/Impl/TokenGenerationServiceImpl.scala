package services.security.Impl

import com.outworkers.phantom.dsl.context
import conf.util.HashcodeKeys
import domain.util.Keys
import org.jose4j.jwa.AlgorithmConstraints
import org.jose4j.jwa.AlgorithmConstraints.ConstraintType
import org.jose4j.jwk.{EcJwkGenerator, EllipticCurveJsonWebKey, JsonWebKey, PublicJsonWebKey}
import org.jose4j.jws.{AlgorithmIdentifiers, JsonWebSignature}
import org.jose4j.jwt.JwtClaims
import org.jose4j.jwt.consumer.JwtConsumerBuilder
import org.jose4j.keys.EllipticCurves
import services.security.{AuthenticationService, TokenGenerationService}
import services.util.KeysService

import scala.concurrent.Future

/**
  * Created by hashcode on 2017/06/10.
  */
class TokenGenerationServiceImpl extends TokenGenerationService{

  override def GenerateKey(phrase: String): PublicJsonWebKey = {
    val key: EllipticCurveJsonWebKey = EcJwkGenerator.generateJwk(EllipticCurves.P256)
    key.setKeyId(phrase)
    key
  }

  override def createClaims(email: String, orgCode: String, role: String, agent: String): JwtClaims = {
    val hashedAgent = AuthenticationService.apply.getHashedPassword(agent)
    val claims = new JwtClaims
    claims.setIssuer(HashcodeKeys.ISSUER)
    claims.setAudience(HashcodeKeys.SITEUSERS)
    claims.setExpirationTimeMinutesInTheFuture(1440)
    claims.setGeneratedJwtId()
    claims.setIssuedAtToNow()
    claims.setNotBeforeMinutesInThePast(2)
    claims.setSubject(HashcodeKeys.SITEACCESS)
    claims.setClaim(HashcodeKeys.EMAIL, email)
    claims.setClaim(HashcodeKeys.ROLE, role)
    claims.setClaim(HashcodeKeys.ORGCODE, orgCode)
    claims.setClaim(HashcodeKeys.AGENT, hashedAgent)
    claims
  }

  private def getPublicKey(publicKey: Option[Keys]) = publicKey match {
    case Some(key)=> getKey(key.value)
    case None => EcJwkGenerator.generateJwk(EllipticCurves.P256)
  }


  override def getToken(claims: JwtClaims): Future[String] = {
    for {
      publicKey <- KeysService.getKeyById(HashcodeKeys.PUBLICKEY)
    } yield {
      val senderJwk = getPublicKey(publicKey)
      val jws = new JsonWebSignature
      jws.setPayload(claims.toJson)
      jws.setKey(senderJwk.getPrivateKey)
      jws.setKeyIdHeaderValue(senderJwk.getKeyId)
      jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.ECDSA_USING_P256_CURVE_AND_SHA256)
      jws.getCompactSerialization
    }
  }

  override def getTokenClaims(token: String): Future[JwtClaims] = {
    for {
      publicKey <- KeysService.getKeyById(HashcodeKeys.PUBLICKEY)
    } yield {
      val senderJwk = getPublicKey(publicKey)
      val jwsAlgConstraints = new AlgorithmConstraints(ConstraintType.WHITELIST, AlgorithmIdentifiers.ECDSA_USING_P256_CURVE_AND_SHA256)
      val jwtConsumer = new JwtConsumerBuilder()
        .setRequireExpirationTime()
        .setAllowedClockSkewInSeconds(30)
        .setRequireSubject()
        .setExpectedIssuer(HashcodeKeys.ISSUER)
        .setExpectedAudience(HashcodeKeys.SITEUSERS)
        .setVerificationKey(senderJwk.getPublicKey)
        .setJwsAlgorithmConstraints(jwsAlgConstraints)
        .build()
      jwtConsumer.processToClaims(token)
    }
  }

  override def getKey(json: String): PublicJsonWebKey = {
     PublicJsonWebKey.Factory.newPublicJwk(json)
  }

  override def getJsonKey(publicJsonWebKey: PublicJsonWebKey): String = {
    publicJsonWebKey.toJson(JsonWebKey.OutputControlLevel.INCLUDE_PRIVATE)
  }

  override def verifyClaims(claims: JwtClaims, agentValue: String): Boolean = {
    val agent = claims.getClaimsMap.get(HashcodeKeys.AGENT)
    AuthenticationService.apply.checkPassword(agentValue,agent.toString)
  }

  override def getClaimFromToken(token: String,claim:String): Future[String] = {
    getTokenClaims(token) map ( claims => claims.getClaimsMap().get(claim).toString)
  }


}
