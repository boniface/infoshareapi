package services.person

import com.outworkers.phantom.dsl.ResultSet
import domain.person.UserDemographics
import repositories.person.UserDemographicsRepository

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

  def getUserDemographics(userId: String): Future[Seq[UserDemographics]] = {
    database.userDemographicsTable.findUserDemographics(userId)
  }

}

object UserDemographicsService extends UserDemographicsService with UserDemographicsRepository
