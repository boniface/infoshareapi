package services.person

import java.util.Date

import domain.person.UserDemographics
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.Await
import scala.concurrent.duration._

class UserDemographicsServiceTest extends FunSuite with BeforeAndAfter{

  val userDemoService = UserDemographicsService
  var userDemoEntity, updateEntity: UserDemographics = _

  before{
    userDemoEntity = UserDemographics(userId="test@email.com",id="1",genderId = "Male",raceId = "African",
      dateOfBirth = new Date(),maritalStatusId = "single",numberOfDependencies = 5,date = new Date(),
      state = "Active")
  }
  test("Create USER_DEMO"){
    val resp = Await.result(userDemoService.save(userDemoEntity),2.minutes)
    assert(resp.isExhausted)
  }

  test("Get USER_DOME BY_ID"){
    val resp = Await.result(userDemoService.getDemoById(Map("userId"-> userDemoEntity.userId,"id"-> userDemoEntity.id)),2.minutes)

    assert(resp.head.id == userDemoEntity.id)
    assert(resp.head.userId  == userDemoEntity.userId)
    assert(resp.head.genderId == userDemoEntity.genderId)
    assert(resp.head.raceId  == userDemoEntity.raceId)
    assert(resp.head.maritalStatusId  == userDemoEntity.maritalStatusId)
    assert(resp.head.numberOfDependencies  == userDemoEntity.numberOfDependencies)
    assert(resp.head.state  == userDemoEntity.state)

  }

  test("UPDATE USER_DEMOGRAPHICS"){
    updateEntity = userDemoEntity.copy(dateOfBirth = new Date(),genderId = "Male")
    Await.result(userDemoService.save(updateEntity), 2.minutes)
    val resp = Await.result(userDemoService.getDemoById(Map("userId"-> userDemoEntity.userId,"id"-> userDemoEntity.id)),2.minutes)

    assert(resp.head.id == userDemoEntity.id)
    assert(resp.head.userId  == userDemoEntity.userId)
    assert(resp.head.genderId == userDemoEntity.genderId)
    assert(resp.head.raceId  == userDemoEntity.raceId)
    assert(resp.head.maritalStatusId  == userDemoEntity.maritalStatusId)
    assert(resp.head.numberOfDependencies  == userDemoEntity.numberOfDependencies)
    assert(resp.head.state  == userDemoEntity.state)

  }

  test("GET ALL USER_DEMOGRAPHICS"){
    val saveResp = Await.result(userDemoService.save(userDemoEntity.copy(id = "10",numberOfDependencies = 10)), 2.minutes)
    val resp = Await.result(userDemoService.getUserDemographics(userDemoEntity.userId),2.minutes)
    assert(resp.size > 1)
    assert(saveResp.isExhausted)
  }

}
