package services.content

import com.outworkers.phantom.dsl.ResultSet
import domain.content.EditedContent
import repositories.content.EditedContentRepository

import scala.concurrent.Future


trait EditedContentService extends EditedContentRepository{

  def save(cont : EditedContent): Future[ResultSet] = {
    database.editedContentTable.save(cont)
  }
  def getContentById(map: Map[String,String]): Future[Option[EditedContent]] = {
    database.editedContentTable.getContentById(map)
  }

  def getAllContents(org: String): Future[Seq[EditedContent]] = {
    database.editedContentTable.getAllContents(org)
  }

  def getContents(org:String, startValue: Int): Future[Iterator[EditedContent]] = {
    database.editedContentTable.getContents(org, startValue)
  }

}

object EditedContentService extends EditedContentService with EditedContentRepository