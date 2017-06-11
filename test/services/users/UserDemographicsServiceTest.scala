package services.users

import java.time.{LocalDateTime=>Date}

import domain.users.UserDemographics
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.Await
import scala.concurrent.duration._

class UserDemographicsServiceTest extends FunSuite with BeforeAndAfter{

  val userDemoService = UserDemographicsService
  var userDemoEntity, updateEntity: UserDemographics = _

  before{
    userDemoEntity = UserDemographics(emailId="test@email.com",id="1",genderId = "Male",raceId = "African",
      dateOfBirth = Date.now(),maritalStatusId = "single",numberOfDependencies = 5,date =Date.now(),
      state = "Active")
  }
  test("Create USER_DEMO"){
    val resp = Await.result(userDemoService.save(userDemoEntity),2.minutes)
    assert(resp.isExhausted)
  }

  test("Get USER_DOME BY_ID"){
    val resp = Await.result(userDemoService.getDemoById(Map("emailId"-> userDemoEntity.emailId,"id"-> userDemoEntity.id)),2.minutes)

    assert(resp.head.id == userDemoEntity.id)
    assert(resp.head.emailId  == userDemoEntity.emailId)
    assert(resp.head.genderId == userDemoEntity.genderId)
    assert(resp.head.raceId  == userDemoEntity.raceId)
    assert(resp.head.maritalStatusId  == userDemoEntity.maritalStatusId)
    assert(resp.head.numberOfDependencies  == userDemoEntity.numberOfDependencies)
    assert(resp.head.state  == userDemoEntity.state)

  }

  test("UPDATE USER_DEMOGRAPHICS"){
    updateEntity = userDemoEntity.copy(dateOfBirth = Date.now(),genderId = "Male")
    val update = Await.result(userDemoService.save(updateEntity), 2.minutes)
    assert(update.isExhausted)

    val resp = Await.result(userDemoService.getDemoById(Map("emailId"-> userDemoEntity.emailId,"id"-> userDemoEntity.id)),2.minutes)

    assert(resp.head.id == userDemoEntity.id)
    assert(resp.head.emailId  == userDemoEntity.emailId)
    assert(resp.head.genderId == userDemoEntity.genderId)
    assert(resp.head.raceId  == userDemoEntity.raceId)
    assert(resp.head.maritalStatusId  == userDemoEntity.maritalStatusId)
    assert(resp.head.numberOfDependencies  == userDemoEntity.numberOfDependencies)
    assert(resp.head.state  == userDemoEntity.state)

  }

  test("GET ALL USER_DEMOGRAPHICS"){
    val saveResp = Await.result(userDemoService.save(userDemoEntity.copy(id = "10",numberOfDependencies = 10)), 2.minutes)
    val resp = Await.result(userDemoService.getUserDemographics(userDemoEntity.emailId),2.minutes)
    assert(resp.size > 1)
    assert(saveResp.isExhausted)
  }

}
