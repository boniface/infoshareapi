package services.util

import com.outworkers.phantom.dsl.ResultSet
import domain.util.Roles
import repositories.util.RolesRepository

import scala.concurrent.Future

/**
  * Created by hashcode on 2017/01/29.
  */
trait RolesService extends RolesRepository {

  def save(role: Roles): Future[ResultSet] = {
    database.rolesTable.save(role)
  }

  def getRoleById(id: String): Future[Option[Roles]] = {
    database.rolesTable.getRoleById(id)
  }

  def getRoles: Future[Seq[Roles]] = {
    database.rolesTable.getRoles
  }

}

object RolesService extends RolesService with RolesRepository