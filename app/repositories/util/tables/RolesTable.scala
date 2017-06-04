package repositories.util.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.demographics.Role

import scala.concurrent.Future

abstract class RoleTable extends Table[RoleTable, Role] {

  object id extends StringColumn with PartitionKey

  object rolename extends StringColumn

}

abstract class RoleTableImpl extends RoleTable with RootConnector {

  override lazy val tableName = "roles"

  def save(role: Role): Future[ResultSet] = {
    insert
      .value(_.id, role.id)
      .value(_.rolename, role.name)
      .future()
  }

  def getRoleById(id: String): Future[Option[Role]] = {
    select.where(_.id eqs id).one()
  }

  def getRoles: Future[Seq[Role]] = {
    select.fetchEnumerator() run Iteratee.collect()
  }
}
