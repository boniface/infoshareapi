package repositories.person.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.person.UserImages

import scala.concurrent.Future

/**
  * Images uploaded by a particular user
*/
class UserImagesTable extends CassandraTable[UserImagesTable, UserImages] {
  /** setting up or defining Person images table attributes */

  object org extends StringColumn(this) with PartitionKey

  object personId extends StringColumn(this) with PrimaryKey

  object id extends StringColumn(this) with PrimaryKey

  object url extends StringColumn(this)

  object description extends StringColumn(this)

  object size extends OptionalStringColumn(this)

  object mime extends StringColumn(this)

  object date extends DateColumn(this)

}

abstract class UserImagesTableImpl extends UserImagesTable with RootConnector {
  override lazy val tableName = "personImages"

  def save(image: UserImages): Future[ResultSet] = {
    /**save new Person Demographics record to the db */
    insert
      .value(_.org, image.org)
      .value(_.personId, image.personId)
      .value(_.id, image.id)
      .value(_.url, image.url)
      .value(_.size, image.size)
      .value(_.mime, image.mime)
      .value(_.description, image.description)
      .value(_.date, image.date)
      .future()
  }

  def getPersonImage(org: String, personId: String, id: String): Future[Option[UserImages]] = {
    /** gets images base on user id organization and db record id */
    select.where(_.org eqs org).and(_.personId eqs personId).and(_.id eqs id).one()
  }

  def getPersonImages(org: String, personId: String): Future[Seq[UserImages]] = {
    /** gets all images base on user id and organization id */
    select.where(_.org eqs org).and(_.personId eqs personId) fetchEnumerator() run Iteratee.collect()
  }

  def getCompanyPeopleImages(org: String): Future[Seq[UserImages]] = {
    /** gets all images for that organization base on org id */
    select.where(_.org eqs org) fetchEnumerator() run Iteratee.collect()
  }
}
