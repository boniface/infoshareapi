package repositories.demographics.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.demographics.Gender

import scala.concurrent.Future

abstract class GenderTable extends Table[GenderTable,Gender]{
  object id extends StringColumn with PartitionKey

  object name extends StringColumn

  object state extends StringColumn

}

abstract class GenderTableImpl extends GenderTable with RootConnector {
  override lazy val tableName = "gender"

  def save(gender: Gender): Future[ResultSet] = {
    insert
      .value(_.id, gender.id)
      .value(_.name, gender.name)
      .value(_.state, gender.state)
      .future()
  }

  def findById(id: String):Future[Option[Gender]] = {
    select.where(_.id eqs id).one()
  }
  def findAll: Future[Seq[Gender]] = {
    select.fetchEnumerator() run Iteratee.collect()
  }

  def deleteById(id:String): Future[ResultSet] = {
    delete.where(_.id eqs id).future()
  }
}
