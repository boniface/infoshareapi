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

  def getById(map: Map[String, String]): Future[Option[UserRole]] = {
    database.userRoleTable.getById(map)
  }

  def getAll(emailId: String): Future[Seq[UserRole]] = {
    database.userRoleTable.getAll(emailId)
  }

  def delete(map: Map[String, String]): Future[ResultSet] = {
    database.userRoleTable.deleteById(map)
  }
}
@Singleton
object UserRoleService extends UserRoleService with UserRoleRepository
