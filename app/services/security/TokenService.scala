package services.security

import com.outworkers.phantom.dsl.ResultSet
import domain.security.Token
import repositories.util.TokenRepository

import scala.concurrent.Future

/**
  * Created by hashcode on 6/24/17.
  */
trait TokenService extends TokenRepository {

  def save(token: Token): Future[ResultSet] = {
    database.tokenTable.save(token)

  }

  def getTokenById(id: String): Future[Option[Token]] = {
    database.tokenTable.getTokenById(id)

  }

  def getAllTokens: Future[Seq[Token]] = {
    database.tokenTable.getAllTokens

  }

  def invalidateToken(id: String): Future[ResultSet] = {
    database.tokenTable.invalidateToken(id)
  }

}

object TokenService extends TokenService with TokenRepository
