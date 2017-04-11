package repositories.util.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.util.Keys

import scala.concurrent.Future

/**
  * Created by /**
  * Created by kuminga on 2016/08/29.
  */
  */
sealed class KeysTable extends CassandraTable[KeysTable, Keys] {

  object id extends StringColumn(this) with PartitionKey

  object value extends StringColumn(this)

  object status extends StringColumn(this)


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
