package repositories.demographics.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.demographics.Role

import scala.concurrent.Future

abstract class RoleTable extends Table[RoleTable, Role] {

  object id extends StringColumn with PartitionKey

  object name extends StringColumn

  object description extends StringColumn

  object state extends StringColumn
}

abstract class RoleTableImpl extends RoleTable with RootConnector {
  override lazy val tableName = "demo_role"

  def save(role: Role): Future[ResultSet] = {
    insert
      .value(_.id, role.id)
      .value(_.name, role.name)
      .value(_.description, role.description)
      .value(_.state, role.state)
      .future()
  }

  def getRoleById(id: String): Future[Option[Role]] = {
    select.where(_.id eqs id).one()
  }

  def getRoles: Future[Seq[Role]] = {
    select.fetchEnumerator() run Iteratee.collect()
  }

  def deleteById(id: String): Future[ResultSet] = {
    delete.where(_.id eqs id).future()
  }
}
