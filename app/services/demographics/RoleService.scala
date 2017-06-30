package services.demographics

import javax.inject.Singleton

import com.outworkers.phantom.dsl.ResultSet
import domain.security.Roles
import repositories.demographics.RoleRepository

import scala.concurrent.Future

trait RoleService extends RoleRepository {

  def save(obj: Roles): Future[ResultSet] = {
    database.roleTable.save(obj)
  }

  def getById(id: String): Future[Option[Roles]] = {
    database.roleTable.getRoleById(id)
  }
  def getAll: Future[Seq[Roles]] = {
    database.roleTable.getRoles
  }

  def delete(id: String): Future[ResultSet] = {
    database.roleTable.deleteById(id)
  }

}

@Singleton
object RoleService extends RoleService with RoleRepository
