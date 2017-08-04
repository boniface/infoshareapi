package repositories.content.tables

import java.time.LocalDateTime
import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8._
import com.outworkers.phantom.streams._
import domain.content.RawContent

import scala.concurrent.Future

abstract class RawContentTable extends Table[RawContentTable, RawContent] {

  object org extends StringColumn with PartitionKey

  object id extends StringColumn with PrimaryKey

  object dateCreated extends Col[LocalDateTime]

  object creator extends StringColumn

  object source extends StringColumn

  object category extends StringColumn

  object title extends StringColumn

  object content extends StringColumn

  object contentTypeId extends StringColumn

  object status extends StringColumn

  object state extends StringColumn

}

abstract class RawContentTableImpl extends RawContentTable with RootConnector {
  override lazy val tableName = "rawCont"

  def save(content: RawContent): Future[ResultSet] = {
    insert
      .value(_.org, content.org)
      .value(_.id, content.id)
      .value(_.dateCreated, content.dateCreated)
      .value(_.creator, content.creator)
      .value(_.source, content.source)
      .value(_.category, content.category)
      .value(_.title, content.title)
      .value(_.content, content.content)
      .value(_.contentTypeId, content.contentTypeId)
      .value(_.status, content.status)
      .value(_.state, content.state)
      .future()
  }

  def getById(map: Map[String, String]): Future[Option[RawContent]] = {
    select.where(_.org eqs map("org")).and(_.id eqs map("id")).one()
  }

  def getAll(org: String): Future[Seq[RawContent]] = {
    select.where(_.org eqs org).fetchEnumerator() run Iteratee.collect()
  }

  def getPaginatedContents(org: String, startValue: Int): Future[Iterator[RawContent]] = {
    select.where(_.org eqs org).fetchEnumerator() run Iteratee.slice(startValue, 20)
  }
}
