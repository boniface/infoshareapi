package repositories.organisation.tables

import java.time.{LocalDateTime => Date}
import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8._
import com.outworkers.phantom.streams._
import domain.organisation.Location

import scala.concurrent.Future

abstract class LocationTable extends Table[LocationTable, Location] {

  object org extends StringColumn with PartitionKey

  object id extends StringColumn with PrimaryKey

  object name extends StringColumn

  object locationTypeId extends StringColumn

  object code extends StringColumn

  object latitude extends StringColumn

  object longitude extends StringColumn

  object parentId extends StringColumn

  object state extends StringColumn

  object date extends Col[Date]

}

abstract class LocationTableImpl extends LocationTable with RootConnector {
  override lazy val tableName = "location"

  def save(location: Location): Future[ResultSet] = {
    insert
      .value(_.org, location.org)
      .value(_.id, location.id)
      .value(_.name, location.name)
      .value(_.locationTypeId, location.locationTypeId)
      .value(_.code, location.code)
      .value(_.latitude, location.latitude)
      .value(_.longitude, location.longitude)
      .value(_.parentId, location.parentId)
      .value(_.state, location.state)
      .value(_.date, location.date)
      .future()
  }

  def findById(map: Map[String, String]): Future[Option[Location]] = {
    select.where(_.org eqs map("org")).and(_.id eqs map("id")).one()
  }

  def findAll(org: String): Future[Seq[Location]] = {
    select.where(_.org eqs org).fetchEnumerator() run Iteratee.collect()
  }

  def deleteById(map: Map[String, String]): Future[ResultSet] = {
    delete.where(_.org eqs map("org")).and(_.id eqs map("id")).future()
  }
}
