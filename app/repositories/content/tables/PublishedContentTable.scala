package repositories.content.tables

import java.time.LocalDateTime
import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8._
import com.outworkers.phantom.streams._
import domain.content.PublishedContent

import scala.concurrent.Future

abstract class PublishedContentTable extends Table[PublishedContentTable, PublishedContent] {

  object org extends StringColumn with PartitionKey

  object id extends StringColumn with PrimaryKey

  object dateCreated extends Col[LocalDateTime]

  object creator extends StringColumn

  object source extends StringColumn

  object category extends StringColumn

  object title extends StringColumn

  object content extends StringColumn

  object contentType extends StringColumn

  object status extends StringColumn

  object state extends StringColumn

}

abstract class PublishedContentTableImpl extends PublishedContentTable with RootConnector {
  override lazy val tableName = "publishedCont"

  def save(content: PublishedContent): Future[ResultSet] = {
    insert
      .value(_.org, content.org)
      .value(_.id, content.id)
      .value(_.dateCreated, content.dateCreated)
      .value(_.creator, content.creator)
      .value(_.source, content.source)
      .value(_.category, content.category)
      .value(_.title, content.title)
      .value(_.content, content.content)
      .value(_.contentType, content.contentType)
      .value(_.status, content.status)
      .value(_.state, content.state)
      .future()
  }

  def getById(
      map: Map[String, String]): Future[Option[PublishedContent]] = {
    select.where(_.org eqs map("org")).and(_.id eqs map("id")).one()
  }

  def getAll(org: String): Future[Seq[PublishedContent]] = {
    select.where(_.org eqs org).fetchEnumerator() run Iteratee.collect()
  }

  def getPaginatedContents(org: String, startValue: Int): Future[Iterator[PublishedContent]] = {
    select.where(_.org eqs org).fetchEnumerator() run Iteratee.slice(startValue, 20)
  }
}
