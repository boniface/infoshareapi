package services.util

import com.outworkers.phantom.dsl.ResultSet
import domain.util.Roles
import repositories.util.RoleRepository

import scala.concurrent.Future

/**
  * Created by hashcode on 2017/01/29.
  */
trait RoleService extends RoleRepository {

  def save(role: Roles): Future[ResultSet] = {

    database.roleTable.save(role)
  }

  def getRoleById(id: String): Future[Option[Roles]] = {

    database.roleTable.getRoleById(id)
  }

  def getRoles: Future[Seq[Roles]] = {

    database.roleTable.getRoles
  }

}

object RoleService extends RoleService with RoleRepository
