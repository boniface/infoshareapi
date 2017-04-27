package repositories.person.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.person.UserRole

import scala.concurrent.Future


class UserRoleTable extends CassandraTable[UserRoleTable, UserRole] {
  /** setting up or defining Person Role table attributes */

  object userId extends StringColumn(this) with PartitionKey
  object roleId extends StringColumn(this) with PrimaryKey
  object state extends StringColumn(this)

}

abstract class UserRoleTableImpl extends UserRoleTable with RootConnector {
  override lazy val tableName = "personRoles"

 def save(role: UserRole): Future[ResultSet] = {
   /**save new user account to the db */

   insert
      .value(_.userId, role.userId)
      .value(_.roleId, role.roleId)
      .value(_.state, role.state)
      .future()
  }


  def findRole(id: String,roleId:String): Future[Option[UserRole]] = {
    /** gets user role base on user id given */
    select.where(_.userId eqs id).and(_.roleId eqs roleId).one
  }

  def findRolesByUserId(userId: String): Future[Seq[UserRole]] = {
    /** get all user roles base on the user id */
    select.where(_.userId eqs userId).fetchEnumerator() run Iteratee.collect()
  }
  def deleteById(id: String,roleId:String): Future[ResultSet] = {
    /** delete user role base on the user id and role id */
    delete.where(_.userId eqs id).and(_.roleId eqs roleId).future()
  }
}
