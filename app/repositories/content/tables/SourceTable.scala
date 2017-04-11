package repositories.content.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.content.Source

import scala.concurrent.Future


class SourceTable extends CassandraTable[SourceTable, Source] {

  object org extends StringColumn(this) with PartitionKey

  object id extends StringColumn(this) with PrimaryKey

  object name extends StringColumn(this)

  object description extends StringColumn(this)

}

abstract class SourceTableImpl extends SourceTable with RootConnector {
  override lazy val tableName = "sources"

  def save(source: Source): Future[ResultSet] = {
    insert
      .value(_.org, source.org)
      .value(_.id, source.id)
      .value(_.name, source.name)
      .value(_.description, source.description)
      .future()
  }

  def getSourceById(org:String,id: String): Future[Option[Source]] = {
    select.where(_.org eqs org).and(_.id eqs id).one()
  }

  def getAllSources(org:String): Future[Seq[Source]] = {
    select.where(_.org eqs org).fetchEnumerator() run Iteratee.collect()
  }
}
