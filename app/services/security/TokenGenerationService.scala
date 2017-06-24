package services.security

import org.jose4j.jwk.PublicJsonWebKey
import org.jose4j.jwt.JwtClaims
import services.security.Impl.TokenGenerationServiceImpl

import scala.concurrent.Future

/**
  * Created by hashcode on 6/24/17.
  */
trait TokenGenerationService {

  def GenerateKey(phrase: String): PublicJsonWebKey

  def createClaims(email: String, orgCode: String, role: String, agent: String):JwtClaims

  def getToken(claims: JwtClaims): Future[String]

  def getTokenClaims(token: String): Future[JwtClaims]

  def verifyClaims(claims: JwtClaims,agent:String):Boolean

  def getKey(json:String):PublicJsonWebKey

  def getJsonKey(publicJsonWebKey: PublicJsonWebKey):String

  def getClaimFromToken(token: String,claim:String):Future[String]

}

object TokenGenerationService {
  def apply: TokenGenerationService = new TokenGenerationServiceImpl()
}
