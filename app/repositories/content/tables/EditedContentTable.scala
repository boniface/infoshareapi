package repositories.content.tables

import java.time.{LocalDateTime => Date}
import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8._
import com.outworkers.phantom.streams._
import domain.content.EditedContent

import scala.concurrent.Future

abstract class EditedContentTable extends Table[EditedContentTable, EditedContent] {

  object org extends StringColumn with PartitionKey

  object id extends StringColumn with PrimaryKey

  object dateCreated extends Col[Date]

  object creator extends StringColumn

  object source extends StringColumn

  object category extends StringColumn

  object title extends StringColumn

  object content extends StringColumn

  object contentType extends StringColumn

  object status extends StringColumn

  object state extends StringColumn

}

abstract class EditedContentTableImpl extends EditedContentTable with RootConnector {
  override lazy val tableName = "editedCont"

  def save(content: EditedContent): Future[ResultSet] = {
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

  def getContentById(map: Map[String,String]): Future[Option[EditedContent]] = {
    select.where(_.org eqs map("org")).and(_.id eqs map("id")).one()
  }

  def getAllContents(org:String): Future[Seq[EditedContent]] = {
    select.where(_.org eqs org).fetchEnumerator() run Iteratee.collect()
  }

  def getPaginatedContents(org:String, startValue: Int): Future[Iterator[EditedContent]] = {
    select.where(_.org eqs org).fetchEnumerator() run Iteratee.slice(startValue, 20)
  }
}
