package repositories.demographics.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.demographics.Race

import scala.concurrent.Future

class RaceTable extends CassandraTable[RaceTable,Race]{
  object id extends StringColumn(this) with PartitionKey
  object name extends StringColumn(this)
  object state extends StringColumn(this)

}

abstract class RaceTableImpl extends RaceTable with RootConnector {
  override lazy val tableName = "race"

  def save(race: Race): Future[ResultSet] = {
    insert
      .value(_.id, race.id)
      .value(_.name, race.name)
      .value(_.state, race.state)
      .future()
  }

  def findById(id: String):Future[Option[Race]] = {
    select.where(_.id eqs id).one()
  }
  def findAll: Future[Seq[Race]] = {
    select.fetchEnumerator() run Iteratee.collect()
  }

  def deleteById(id:String): Future[ResultSet] = {
    delete.where(_.id eqs id).future()
  }
}
