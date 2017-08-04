package services.users

import java.time.LocalDateTime

import domain.users.UserDemographics
import org.scalatest.{BeforeAndAfter, FunSuite}
import util.factories

import scala.concurrent.Await
import scala.concurrent.duration._

class UserDemographicsServiceTest extends FunSuite with BeforeAndAfter{

  val service = UserDemographicsService
  var entity, updateEntity: UserDemographics = _
  var kwargs: Map[String,String] =  _

  before{
    entity = factories.getUserDemographics
    kwargs = Map("emailId"-> entity.emailId,"id"-> entity.id)
  }

  test("Create USER_DEMO"){
    val resp = Await.result(service.save(entity),2.minutes)
    assert(resp.isExhausted)
  }

  test("Get USER_DOME BY_ID"){
    val resp = Await.result(service.getDemoById(kwargs),2.minutes)
    assert(resp.get equals entity)
  }

  test("UPDATE USER_DEMOGRAPHICS"){
    updateEntity = entity.copy(dateOfBirth = LocalDateTime.now(),genderId = "Male")
    val update = Await.result(service.save(updateEntity), 2.minutes)
    val resp = Await.result(service.getDemoById(kwargs),2.minutes)

    assert(update.isExhausted)
    assert(resp.get != entity)
    assert(resp.get equals updateEntity)

  }

  test("GET ALL USER_DEMOGRAPHICS"){
    val saveResp = Await.result(service.save(entity.copy(id = "10",numberOfDependencies = 10)), 2.minutes)
    val resp = Await.result(service.getUserDemographics(entity.emailId),2.minutes)

    assert(resp.nonEmpty)
    assert(saveResp.isExhausted)
  }

}
