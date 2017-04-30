package repositories.users.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.users.UserRole

import scala.concurrent.Future


sealed class UserRoleTable extends CassandraTable[UserRoleTable, UserRole] {
  /** setting up or defining Person Role table attributes */

  object emailId extends StringColumn(this) with PartitionKey
  object roleId extends StringColumn(this) with PrimaryKey
  object state extends StringColumn(this)

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


  def findRole(map: Map[String, String]): Future[Option[UserRole]] = {
    /** gets user role base on user id given */
    select.where(_.emailId eqs map("emailId")).and(_.roleId eqs map("roleId")).one
  }

  def findRolesByemailId(emailId: String): Future[Seq[UserRole]] = {
    /** get all user roles base on the user id */
    select.where(_.emailId eqs emailId).fetchEnumerator() run Iteratee.collect()
  }
  def deleteById(map: Map[String, String]): Future[ResultSet] = {
    /** delete user role base on the user id and role id */
    delete.where(_.emailId eqs map("emailId")).and(_.roleId eqs map("roleId")).future()
  }
}
