package repositories.util.tables

import java.time.LocalDateTime

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8._
import com.outworkers.phantom.streams._
import domain.util.ItemStatus

import scala.concurrent.Future

/**
  * Created by hashcode on 2016/12/16.
  * itemId: String,
  * date: DateTime,
  * status: String,
  * description: String
  */
abstract class ItemStatusTable extends Table[ItemStatusTable, ItemStatus] {

  object itemId extends StringColumn with PartitionKey

  object date extends Col[LocalDateTime] with PrimaryKey with ClusteringOrder with Ascending

  object status extends StringColumn

  object description extends StringColumn

  override def fromRow(r: Row): ItemStatus = {
    ItemStatus(itemId(r), date(r), status(r), description(r))
  }
}

abstract class ItemStatusTableImpl extends ItemStatusTable with RootConnector {
  override lazy val tableName = "status"



  def save(status: ItemStatus): Future[ResultSet] = {
    insert
      .value(_.itemId, status.itemId)
      .value(_.date, status.date)
      .value(_.status, status.status)
      .value(_.description, status.description)
      .future()
  }

  def getStatus(itemId: String): Future[Seq[ItemStatus]] = {
    select.where(_.itemId eqs itemId).fetchEnumerator() run Iteratee.collect()
  }
}

