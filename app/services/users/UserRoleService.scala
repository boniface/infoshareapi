package services.users

import com.outworkers.phantom.dsl.ResultSet
import domain.users.UserRole
import repositories.users.UserRoleRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

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

  def deleteUserRoles(emailId:String):Future[ResultSet] ={
    database.userRoleTable.deleteUserRoles(emailId)
  }

}

object UserRoleService extends UserRoleService with UserRoleRepository
