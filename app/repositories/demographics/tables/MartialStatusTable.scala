package repositories.demographics.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.demographics.MaritalStatus

import scala.concurrent.Future


abstract class MartialStatusTable extends Table[MartialStatusTable,MaritalStatus] {

  object id extends StringColumn with PartitionKey

  object name extends StringColumn

  object state extends StringColumn

}

abstract class MaritalStatusTableImpl extends MartialStatusTable with RootConnector {
  override lazy val tableName = "martialStatus"

  def save(obj: MaritalStatus): Future[ResultSet] = {
    insert
      .value(_.id, obj.id)
      .value(_.name, obj.name)
      .value(_.state, obj.state)
      .future()
  }

  def getRoleById(id: String): Future[Option[MaritalStatus]] = {
    select.where(_.id eqs id).one()
  }

  def getRoles: Future[Seq[MaritalStatus]] = {
    select.fetchEnumerator() run Iteratee.collect()
  }

  def deleteById(id: String): Future[ResultSet] = {
    delete.where(_.id eqs id).future()
  }
}