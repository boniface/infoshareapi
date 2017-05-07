package repositories.content.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.content.EditedContent

import scala.concurrent.Future

class EditedContentTable extends CassandraTable[EditedContentTable, EditedContent] {

  object org extends StringColumn(this) with PartitionKey

  object id extends StringColumn(this) with PrimaryKey

  object dateCreated extends DateColumn(this)

  object creator extends StringColumn(this)

  object source extends StringColumn(this)

  object category extends StringColumn(this)

  object title extends StringColumn(this)

  object content extends StringColumn(this)

  object contentType extends StringColumn(this)

  object status extends StringColumn(this)

  object state extends StringColumn(this)

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
