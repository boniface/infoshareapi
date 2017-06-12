package repositories.content.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.content.Source

import scala.concurrent.Future

abstract class SourceTable extends Table[SourceTable, Source] {

  object org extends StringColumn with PartitionKey

  object id extends StringColumn with PrimaryKey

  object name extends StringColumn

  object description extends StringColumn

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

  def getSourceById(map: Map[String, String]): Future[Option[Source]] = {
    select.where(_.org eqs map("org")).and(_.id eqs map("id")).one()
  }

  def getAllSources(org: String): Future[Seq[Source]] = {
    select.where(_.org eqs org).fetchEnumerator() run Iteratee.collect()
  }
}
