package repositories.util.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.util.Keys

import scala.concurrent.Future


abstract class KeysTable extends Table[KeysTable, Keys] {

  object id extends StringColumn with PartitionKey

  object value extends StringColumn

  object status extends StringColumn


}

abstract class KeysTableImpl extends KeysTable with RootConnector {

  override lazy val tableName = "tokenkeys"


  def save(key: Keys): Future[ResultSet] = {
    insert
      .value(_.id, key.id)
      .value(_.value, key.value)
      .value(_.status, key.status)
      .future()
  }

  def getKeyById(id: String): Future[Option[Keys]] = {
    select.where(_.id eqs id).one()
  }

  def getAllkeys: Future[Seq[Keys]] = {
    select.fetchEnumerator() run Iteratee.collect()
  }

}
