package repositories.demographics.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.demographics.Role

import scala.concurrent.Future


class RoleTable extends CassandraTable[RoleTable, Role] {

  object id extends StringColumn(this) with PartitionKey

  object name extends StringColumn(this)

  object description extends StringColumn(this)

  object state extends StringColumn(this)

}

abstract class RoleTableImpl extends RoleTable with RootConnector {
  override lazy val tableName = "role"

  def save(role: Role): Future[ResultSet] = {
    insert
      .value(_.id,role.id)
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
}

