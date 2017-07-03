package api.users

import java.time.LocalDateTime

import domain.users.UserDemographics
import org.scalatest.{BeforeAndAfter, FunSuite}
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._
import util.UtilTest

class UserDemographicsCtrlTest extends FunSuite with BeforeAndAfter with GuiceOneAppPerTest {

  var entity, updateEntity : UserDemographics = _
  var baseUrl = "/users/demographics/"
  val title = "demographics"

  before {
    entity =  UserDemographics(emailId="test@email.com",id="1",genderId = "1",raceId = "1",
      dateOfBirth = LocalDateTime.now(),maritalStatusId = "2",numberOfDependencies = 5,date =LocalDateTime.now(),
      state = "Active")
  }

  test("Create "+title){
    val request = route(app, FakeRequest(POST, baseUrl + "create")
      .withJsonBody(Json.toJson(entity))
      .withHeaders(UtilTest.getHeaders:_*)
    ).get

    assert(status(request) equals OK)
    assert(contentAsString(request) equals Json.toJson(entity).toString())
  }

  test("update "+title){
    updateEntity = entity.copy(genderId = "12",raceId = "2",maritalStatusId = "1")
    val request = route(app, FakeRequest(POST, baseUrl+"create")
      .withJsonBody(Json.toJson(updateEntity))
      .withHeaders(UtilTest.getHeaders:_*)
    ).get
    assert(status(request) equals OK)
    assert(contentAsString(request) != Json.toJson(entity).toString())
    assert(contentAsString(request) equals Json.toJson(updateEntity).toString())
  }


  test("get "+title+ " by id"){
    val request = route(app, FakeRequest(GET, baseUrl + entity.emailId + "/" + entity.id)
      .withHeaders(UtilTest.getHeaders:_*)
    ).get
    assert(status(request) equals OK)
    assert(!contentAsJson(request).result.isEmpty)
  }

  test("get all organisation "+title+"'s"){
    val request = route(app, FakeRequest(GET, baseUrl + "all/" + entity.emailId)
      .withHeaders(UtilTest.getHeaders:_*)
    ).get
    assert(status(request) equals OK)
  }

}
