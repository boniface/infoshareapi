package repositories.users.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.users.UserStatus

import scala.concurrent.Future

/**
 * Created by Rosie on 2016/11/14.
 */
class UserStatusTable  extends CassandraTable[UserStatusTable,UserStatus]{
  object userId extends StringColumn(this) with PartitionKey
  object date extends DateTimeColumn(this)  with PrimaryKey with ClusteringOrder with Ascending
  object status extends StringColumn(this)
  }

abstract class  UserStatusTableImpl extends UserStatusTable with RootConnector {

  override lazy val tableName = "userStatus"

  def save(userstatus: UserStatus): Future[ResultSet] = {
    insert
      .value(_.userId, userstatus.userId)
      .value(_.status, userstatus.status)
      .value(_.date, userstatus.date)
      .future()
  }


  def getUserStatuseHistory(userId: String): Future[Seq[UserStatus]] = {
    select
      .where(_.userId eqs userId)
      .fetchEnumerator() run Iteratee.collect()
  }

}
