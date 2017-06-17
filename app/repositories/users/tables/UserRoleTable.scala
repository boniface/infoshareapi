package repositories.users.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.users.UserRole

import scala.concurrent.Future


abstract class UserRoleTable extends Table[UserRoleTable, UserRole] {

  object emailId extends StringColumn with PartitionKey

  object roleId extends StringColumn with PrimaryKey

  object state extends StringColumn

}

abstract class UserRoleTableImpl extends UserRoleTable with RootConnector {
  override lazy val tableName = "userRole"

 def save(role: UserRole): Future[ResultSet] = {
   insert
      .value(_.emailId, role.emailId)
      .value(_.roleId, role.roleId)
      .value(_.state, role.state)
      .future()
  }


  def getById(map: Map[String, String]): Future[Option[UserRole]] = {
    select.where(_.emailId eqs map("emailId")).and(_.roleId eqs map("roleId")).one
  }

  def getAll(emailId: String): Future[Seq[UserRole]] = {
    select.where(_.emailId eqs emailId).fetchEnumerator() run Iteratee.collect()
  }
  def deleteById(map: Map[String, String]): Future[ResultSet] = {
    delete.where(_.emailId eqs map("emailId")).and(_.roleId eqs map("roleId")).future()
  }
}
