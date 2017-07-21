package services.users

import javax.inject.Singleton

import com.outworkers.phantom.dsl.ResultSet
import domain.users.{User, UserRole}
import repositories.users.UserRoleRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait UserRoleService extends UserRoleRepository {

  def save(role: UserRole): Future[ResultSet] = {
    database.userRoleTable.save(role)
  }

  def getUserRoles(user:User): Future[Seq[UserRole]] = {
    database.userRoleTable.getUserRoles(user.siteId,user.email)
  }

  def getUserRole(user:User): Future[UserRole] = {
    database.userRoleTable.getUserRoles(user.siteId,user.email).map(role => role.head)
  }

  def deleteUserRoles(user:User): Future[ResultSet] ={
    database.userRoleTable.deleteUserRoles(user.siteId,user.email)
  }

}

@Singleton
object UserRoleService extends UserRoleService with UserRoleRepository
