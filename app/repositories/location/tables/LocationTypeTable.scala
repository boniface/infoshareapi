package repositories.location.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.location.LocationType

import scala.concurrent.Future

class LocationTypeTable extends CassandraTable[LocationTypeTable,LocationType]{
  object id extends StringColumn(this) with PartitionKey
  object name extends StringColumn(this)
  object code extends StringColumn(this)
  object state extends StringColumn(this)

}

abstract class LocationTypeTableImpl extends LocationTypeTable with RootConnector {
  override lazy val tableName = "locationTypes"

  def save(locationtypes: LocationType): Future[ResultSet] = {
    insert
      .value(_.id, locationtypes.id)
      .value(_.name, locationtypes.name)
      .value(_.code, locationtypes.code)
      .value(_.state, locationtypes.state)
      .future()
  }

  def findById(id: String):Future[Option[LocationType]] = {
    select.where(_.id eqs id).one()
  }
  def findAll: Future[Seq[LocationType]] = {
    select.fetchEnumerator() run Iteratee.collect()
  }

  def deleteById(id:String): Future[ResultSet] = {
    delete.where(_.id eqs id).future()
  }
}
