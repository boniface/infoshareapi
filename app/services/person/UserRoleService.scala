package services.person

import com.outworkers.phantom.dsl.ResultSet
import domain.person.UserRole
import repositories.person.UserRoleRepository
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future


trait UserRoleService extends UserRoleRepository{

  def save(role: UserRole): Future[ResultSet] = {
    for{
      saveEntity <- database.userRoleTable.save(role)
    } yield saveEntity
  }

  def getUserRole(map: Map[String,String]): Future[Option[UserRole]] = {
    database.userRoleTable.findRole(map)
  }

  def getRolesByUserId(userId: String): Future[Seq[UserRole]] = {
    database.userRoleTable.findRolesByUserId(userId)
  }

  def deleteRole(map: Map[String,String]): Future[ResultSet] = {
    database.userRoleTable.deleteById(map)
  }
}

object UserRoleService extends UserRoleService with UserRoleRepository