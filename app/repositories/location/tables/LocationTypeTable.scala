package repositories.location.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.location.LocationType

import scala.concurrent.Future

abstract class LocationTypeTable extends Table[LocationTypeTable,LocationType]{

  object id extends StringColumn with PartitionKey
 
  object name extends StringColumn
 
  object code extends StringColumn
 
  object state extends StringColumn

}

abstract class LocationTypeTableImpl extends LocationTypeTable with RootConnector {
  override lazy val tableName = "locTypes"

  def save(loc: LocationType): Future[ResultSet] = {
    insert
      .value(_.id, loc.id)
      .value(_.name, loc.name)
      .value(_.code, loc.code)
      .value(_.state, loc.state)
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
