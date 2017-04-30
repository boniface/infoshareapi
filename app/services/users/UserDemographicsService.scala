package services.users

import com.outworkers.phantom.dsl.ResultSet
import domain.users.UserDemographics
import repositories.users.UserDemographicsRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


trait UserDemographicsService extends UserDemographicsRepository {

  def save(userDemo: UserDemographics): Future[ResultSet] ={
    for{
      userDemoEntity <- database.userDemographicsTable.save(userDemo)
    } yield userDemoEntity
  }
  def getDemoById(map: Map[String, String]): Future[Option[UserDemographics]] = {
    database.userDemographicsTable.findById(map)
  }

  def getUserDemographics(emailId: String): Future[Seq[UserDemographics]] = {
    database.userDemographicsTable.findUserDemographics(emailId)
  }

}

object UserDemographicsService extends UserDemographicsService with UserDemographicsRepository
