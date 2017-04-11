package repositories.util.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.util.Roles

import scala.concurrent.Future

class RoleTable extends CassandraTable[RoleTable, Roles] {
  object id extends StringColumn(this) with PartitionKey
  object rolename extends StringColumn(this)
  }

abstract class  RoleTableImpl extends RoleTable with RootConnector {

  override lazy val tableName = "roles"

  def save(role: Roles): Future[ResultSet] = {
    insert
      .value(_.id, role.id)
      .value(_.rolename, role.rolename)
      .future()
  }

  def getRoleById(id: String): Future[Option[Roles]] = {
    select.where(_.id eqs id).one()
  }

  def getRoles: Future[Seq[Roles]] = {
    select.fetchEnumerator() run Iteratee.collect()
  }
}
