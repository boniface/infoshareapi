package services.users

import com.outworkers.phantom.dsl.ResultSet
import domain.users.UserStatus
import repositories.users.UserStatusRepository
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

/**
  * Created by hashcode on 2017/01/29.
  */
trait UserStatusService extends UserStatusRepository {
  def save(status: UserStatus): Future[ResultSet] = {
    database.userStatusTable.save(status)
  }

  def getUserStatusHistory(emailId: String): Future[Seq[UserStatus]] = {
    database.userStatusTable.getUserStatuseHistory(emailId)
  }

  def getUserStatus(emailId: String): Future[UserStatus] = {
    database.userStatusTable.getUserStatuseHistory(emailId).map(status => status.head)
  }

}

object UserStatusService extends UserStatusService with UserStatusRepository
