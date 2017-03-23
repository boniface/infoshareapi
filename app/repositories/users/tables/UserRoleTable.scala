package repositories.users.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.users.UserRole

import scala.concurrent.Future

/**
 * Created by Rosie on 2016/11/14.
 */
class UserRoleTable extends CassandraTable[UserRoleTable,UserRole]{
  object emailId extends StringColumn(this) with PartitionKey
  object date extends DateTimeColumn(this) with PrimaryKey with ClusteringOrder with Ascending
  object roleId extends StringColumn(this)

}

abstract class  UserRoleTableImpl extends UserRoleTable with RootConnector {

  override lazy val tableName = "userroles"

  def save(role: UserRole): Future[ResultSet] = {
    insert
      .value(_.emailId, role.emailId)
      .value(_.roleId, role.roleId)
      .value(_.date,role.date)
      .future()
  }

  def getUserRoles(emailId: String): Future[Seq[UserRole]] = {
    select
      .where(_.emailId eqs emailId)
      .fetchEnumerator() run Iteratee.collect()
  }
}
