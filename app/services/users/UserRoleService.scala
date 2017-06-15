package services.users

import javax.inject.Singleton

import com.outworkers.phantom.dsl.ResultSet
import domain.users.UserRole
import repositories.users.UserRoleRepository

import scala.concurrent.Future

trait UserRoleService extends UserRoleRepository {

  def save(role: UserRole): Future[ResultSet] = {
    database.userRoleTable.save(role)
  }

  def getRolesByemailId(map: Map[String, String]): Future[Option[UserRole]] = {
    database.userRoleTable.findRolesByemailId(map)
  }

  def getUserRole(emailId: String): Future[Seq[UserRole]] = {
    database.userRoleTable.findRole(emailId)
  }

  def deleteRole(map: Map[String, String]): Future[ResultSet] = {
    database.userRoleTable.deleteById(map)
  }
}
@Singleton
object UserRoleService extends UserRoleService with UserRoleRepository
