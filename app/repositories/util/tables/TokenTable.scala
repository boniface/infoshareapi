package repositories.util.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.security.Token

import scala.concurrent.Future


abstract class TokenTable extends Table[TokenTable, Token] {

  object id extends StringColumn with PartitionKey

  object tokenValue extends StringColumn

}

abstract class TokenTableImpl extends TokenTable with RootConnector {
  override lazy val tableName = "tokens"

  def save(token: Token): Future[ResultSet] = {
    insert
      .value(_.id, token.id)
      .value(_.tokenValue, token.tokenValue)
      .ttl(86400)
      .future()
  }

  def getTokenById(id: String): Future[Option[Token]] = {
    select.where(_.id eqs id).one()

  }

  def getAllTokens: Future[Seq[Token]] = {
    select.fetchEnumerator() run Iteratee.collect()
  }

  def invalidateToken(id: String): Future[ResultSet] = {
    delete.where(_.id eqs id).future()
  }

}
