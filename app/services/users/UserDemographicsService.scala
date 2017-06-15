package services.users

import javax.inject.Singleton

import com.outworkers.phantom.dsl.ResultSet
import domain.users.UserDemographics
import repositories.users.UserDemographicsRepository

import scala.concurrent.Future

trait UserDemographicsService extends UserDemographicsRepository {

  def save(userDemo: UserDemographics): Future[ResultSet] = {
    database.userDemographicsTable.save(userDemo)
  }
  def getDemoById(map: Map[String, String]): Future[Option[UserDemographics]] = {
    database.userDemographicsTable.findById(map)
  }

  def getUserDemographics(emailId: String): Future[Seq[UserDemographics]] = {
    database.userDemographicsTable.findUserDemographics(emailId)
  }

}
@Singleton
object UserDemographicsService extends UserDemographicsService with UserDemographicsRepository
