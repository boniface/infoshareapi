package services.content

import com.outworkers.phantom.dsl.ResultSet
import domain.content.Media
import repositories.content.MediaRepository

import scala.concurrent.Future


trait MediaService extends MediaRepository{

  def save(media: Media): Future[ResultSet] = {
      database.mediaTable.save(media)
  }

  def getContentMediaById(map: Map[String,String]):Future[Option[Media]] = {
    database.mediaTable.getContentMediaById(map)
  }
  def getAllContentMedia(contentId: String):Future[Seq[Media]] = {
    database.mediaTable.getAllContentMedia(contentId)
  }
}

object MediaService extends MediaService with MediaRepository
