package services.users

import com.outworkers.phantom.dsl.ResultSet
import domain.users.UserRole
import repositories.users.UserRoleRepository
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future


trait UserRoleService extends UserRoleRepository{

  def save(role: UserRole): Future[ResultSet] = {
    database.userRoleTable.save(role)
  }

  def getUserRole(map: Map[String,String]): Future[Option[UserRole]] = {
    database.userRoleTable.findRole(map)
  }

  def getRolesByemailId(emailId: String): Future[Seq[UserRole]] = {
    database.userRoleTable.findRolesByemailId(emailId)
  }

  def deleteRole(map: Map[String,String]): Future[ResultSet] = {
    database.userRoleTable.deleteById(map)
  }
}

object UserRoleService extends UserRoleService with UserRoleRepository