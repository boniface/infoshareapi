package services.users

import com.outworkers.phantom.dsl.ResultSet
import scala.concurrent.ExecutionContext.Implicits.global
import domain.users.UserRole
import repositories.users.UserRoleRepository

import scala.concurrent.Future

/**
  * Created by hashcode on 2017/01/29.
  */
trait UserRoleService extends UserRoleRepository {
  def save(role: UserRole): Future[ResultSet] = {
    database.userRoleTable.save(role)

  }

  def getUserRoles(emailId: String): Future[Seq[UserRole]] = {
    database.userRoleTable.getUserRoles(emailId)

  }

  def getUserRole(emailId: String): Future[UserRole] = {
    database.userRoleTable.getUserRoles(emailId).map(role => role.head)
  }

}

object UserRoleService extends UserRoleService with UserRoleRepository
