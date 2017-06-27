package repositories.users.tables

import java.time.{LocalDateTime => Date}
import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8._
import com.outworkers.phantom.streams._
import domain.users.UserImages

import scala.concurrent.Future


abstract class UserImagesTable extends Table[UserImagesTable, UserImages] {

  object org extends StringColumn with PartitionKey

  object emailId extends StringColumn with PrimaryKey

  object id extends StringColumn with PrimaryKey

  object url extends StringColumn

  object description extends StringColumn

  object size extends OptionalStringColumn

  object mime extends StringColumn

  object date extends Col[Date]

}

abstract class UserImagesTableImpl extends UserImagesTable with RootConnector {
  override lazy val tableName = "userImg"

  def save(image: UserImages): Future[ResultSet] = {
    insert
      .value(_.org, image.org)
      .value(_.emailId, image.emailId)
      .value(_.id, image.id)
      .value(_.url, image.url)
      .value(_.size, image.size)
      .value(_.mime, image.mime)
      .value(_.description, image.description)
      .value(_.date, image.date)
      .future()
  }

  def getUserImage(map: Map[String, String]): Future[Option[UserImages]] = {
    /** gets images base on user id organization and db record id */
    select.where(_.org eqs map("org")).and(_.emailId eqs map("emailId")).and(_.id eqs map("id")).one()
  }

  def getUserImages(map: Map[String, String]): Future[Seq[UserImages]] = {
    /** gets all images base on user id and organization id */
    select.where(_.org eqs map("org")).and(_.emailId eqs map("emailId")) fetchEnumerator() run Iteratee.collect()
  }

  def getCompanyImages(org: String): Future[Seq[UserImages]] = {
    /** gets all images for that organization base on org id */
    select.where(_.org eqs org) fetchEnumerator() run Iteratee.collect()
  }
}
