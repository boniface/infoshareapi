package repositories.content.tables


import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.content.Media

import scala.concurrent.Future

class MediaTable extends CassandraTable[MediaTable, Media] {

  object contentId extends StringColumn(this) with PartitionKey
  object id extends StringColumn(this) with PrimaryKey
  object description extends StringColumn(this)
  object url extends StringColumn(this)
  object mime extends StringColumn(this)
  object date extends DateColumn(this)
  object state extends StringColumn(this)


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

  def getContentMediaById(map: Map[String,String]):Future[Option[Media]] = {
    select.where(_.contentId eqs map("contentId")).and(_.id eqs map("id")).one()
  }
  def getAllContentMedia(contentId:String):Future[Seq[Media]] = {
    select.where(_.contentId eqs contentId)fetchEnumerator() run Iteratee.collect()
  }

}

