package services.users

import javax.inject.Singleton

import com.outworkers.phantom.dsl.ResultSet
import domain.users.UserImages
import repositories.users.UserImagesRepository

import scala.concurrent.Future

trait UserImageService extends UserImagesRepository {

  def save(userImg: UserImages): Future[ResultSet] = {
    database.userImagesTable.save(userImg)
  }

  def getUserImageById(map: Map[String, String]): Future[Option[UserImages]] = {
    database.userImagesTable.getUserImage(map)
  }

  def getAllUserImages(map: Map[String, String]): Future[Seq[UserImages]] = {
    database.userImagesTable.getUserImages(map)
  }

  def getAllUserCompanyImages(org: String): Future[Seq[UserImages]] = {
    database.userImagesTable.getCompanyImages(org)
  }

}
@Singleton
object UserImageService extends UserImageService with UserImagesRepository
