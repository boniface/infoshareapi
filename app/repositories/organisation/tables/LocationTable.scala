package repositories.organisation.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.organisation.Location

import scala.concurrent.Future


class LocationTable extends CassandraTable[LocationTable, Location] {

  object org extends StringColumn(this) with PartitionKey

  object id extends StringColumn(this) with PrimaryKey

  object name extends StringColumn(this)

  object locationTypeId extends StringColumn(this)

  object code extends StringColumn(this)

  object latitude extends StringColumn(this)

  object longitude extends StringColumn(this)

  object parentId extends StringColumn(this)

  object state extends StringColumn(this)

  object date extends DateColumn(this)

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

  def findById(org: String, id: String): Future[Option[Location]] = {
    select.where(_.org eqs org).and(_.id eqs id).one()
  }

  def findAll(company: String): Future[Seq[Location]] = {
    select.fetchEnumerator() run Iteratee.collect()
  }

  def deleteById(id: String): Future[ResultSet] = {
    delete.where(_.id eqs id).future()
  }
}
