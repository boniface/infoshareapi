package repositories.util.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.util.Token

import scala.concurrent.Future

/**
  * Created by hashcode on 2015/06/09.
  */
class TokenTable extends CassandraTable[TokenTable, Token] {

  object id extends StringColumn(this) with PartitionKey

  object tokenValue extends StringColumn(this)

}

abstract class  TokenTableImpl extends TokenTable with RootConnector {
  override lazy val tableName = "tokens"

  def save(token: Token): Future[ResultSet] = {
    insert
      .value(_.id, token.id)
      .value(_.tokenValue, token.tokenValue)
      .ttl(12000)
      .future()
  }

  def getTokenById(id: String): Future[Option[Token]] = {
    select.where(_.id eqs id).one()

  }

  def getAllTokens: Future[Seq[Token]] = {
    select.fetchEnumerator() run Iteratee.collect()
  }

}
