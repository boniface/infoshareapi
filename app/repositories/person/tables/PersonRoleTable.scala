package repositories.person.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.person.PersonRole

import scala.concurrent.Future


class PersonRoleTable extends CassandraTable[PersonRoleTable, PersonRole] {
  /** setting up or defining Person Role table attributes */

  object personId extends StringColumn(this) with PartitionKey
  object roleId extends StringColumn(this) with PrimaryKey
  object state extends StringColumn(this)

}

abstract class PersonRoleTableImpl extends PersonRoleTable with RootConnector {
  override lazy val tableName = "personRoles"

 def save(role: PersonRole): Future[ResultSet] = {
   /**save new user account to the db */

   insert
      .value(_.personId, role.personId)
      .value(_.roleId, role.roleId)
      .value(_.state, role.state)
      .future()
  }


  def findRole(id: String,roleId:String): Future[Option[PersonRole]] = {
    /** gets user role base on user id given */
    select.where(_.personId eqs id).and(_.roleId eqs roleId).one
  }

  def findRolesByUserId(personId: String): Future[Seq[PersonRole]] = {
    /** get all user roles base on the user id */
    select.where(_.personId eqs personId).fetchEnumerator() run Iteratee.collect()
  }
  def deleteById(id: String,roleId:String): Future[ResultSet] = {
    /** delete user role base on the user id and role id */
    delete.where(_.personId eqs id).and(_.roleId eqs roleId).future()
  }
}
