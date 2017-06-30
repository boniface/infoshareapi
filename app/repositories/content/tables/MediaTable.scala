package repositories.content.tables

import java.time.LocalDateTime
import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8._
import com.outworkers.phantom.streams._
import domain.content.Media

import scala.concurrent.Future

abstract class MediaTable extends Table[MediaTable, Media] {

  object contentId extends StringColumn with PartitionKey

  object id extends StringColumn with PrimaryKey

  object description extends StringColumn

  object url extends StringColumn

  object mime extends StringColumn

  object date extends Col[LocalDateTime]

  object state extends StringColumn

}

abstract class MediaTableImpl extends MediaTable with RootConnector {
  override lazy val tableName = "media"

  def save(media: Media): Future[ResultSet] = {
    insert
      .value(_.contentId, media.contentId)
      .value(_.id, media.id)
      .value(_.description, media.description)
      .value(_.url, media.url)
      .value(_.mime, media.mime)
      .value(_.date, media.date)
      .value(_.state, media.state)
      .future()
  }

  def getById(map: Map[String, String]): Future[Option[Media]] = {
    select
      .where(_.contentId eqs map("contentId")).and(_.id eqs map("id")).one()
  }
  def getAll(contentId: String): Future[Seq[Media]] = {
    select.where(_.contentId eqs contentId) fetchEnumerator () run Iteratee.collect()
  }

}
