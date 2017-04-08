package services.person

import com.datastax.driver.core.ResultSet
import domain.person.PersonRole

import scala.concurrent.Future


object PersonRoleService extends Service {
  def saveOrUpdate(entity: PersonRole): Future[ResultSet] = {
    PersonRoleRepository.save(entity)
  }

  def get(id: String, roleId: String): Future[Option[PersonRole]] = {
    PersonRoleRepository.findRole(id, roleId)
  }

  def getAllRoles(personId: String): Future[Seq[PersonRole]] = {
    PersonRoleRepository.findRolesById(personId)
  }

}
