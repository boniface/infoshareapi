package repositories.util.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.security.Roles

import scala.concurrent.Future


abstract class RoleTable extends Table[RoleTable, Roles] {

  object id extends StringColumn with PartitionKey

  object rolename extends StringColumn

}

abstract class RoleTableImpl extends RoleTable with RootConnector {

  override lazy val tableName = "util_roles"

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
