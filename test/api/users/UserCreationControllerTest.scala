//package api.users
//
//import java.time.{LocalDateTime => Date}
//import domain.users.User
//import org.scalatest.{BeforeAndAfter, FunSuite}
//import org.scalatestplus.play.guice.GuiceOneAppPerTest
//import play.api.libs.json.Json
//import play.api.test.FakeRequest
//import play.api.test.Helpers._
//
//class UserCreationControllerTest extends FunSuite with BeforeAndAfter with GuiceOneAppPerTest {
//
//  var entity, updateEntity : User = _
//  var baseUrl = "/users/usercreation/"
//  val title = "user"
//
//  before {
//    entity = User("CPUT","test@test.com","First Name","Last Name",None,
//      None,"test123","ACTIVE",Date.now() )
//  }
//
//
//  test("create "+title) {
//    val request = route(app, FakeRequest(POST, baseUrl + "create/" + "1")
//      .withJsonBody(Json.toJson(entity))
//      .withHeaders(AUTHORIZATION -> "Token")
//    ).get
//
//    assert(status(request) equals OK)
//    assert(contentAsString(request) equals Json.toJson(entity).toString() )
//  }
//
//  test("register "+title) {
//    val request = route(app, FakeRequest(POST, baseUrl + "register")
//      .withJsonBody(Json.toJson(entity))
//      .withHeaders(AUTHORIZATION -> "Token")
//    ).get
//
//    assert(status(request) equals OK)
//    assert(contentAsString(request) equals Json.toJson(entity).toString() )
//  }
//
//  test("update "+title) {
//    val request = route(app, FakeRequest(POST, baseUrl + "update")
//      .withJsonBody(Json.toJson(entity))
//      .withHeaders(AUTHORIZATION -> "Token")
//    ).get
//
//    assert(status(request) equals OK)
//    assert(contentAsString(request) equals Json.toJson(entity).toString() )
//  }
//
//  test("is "+title+ "registered") {
//    val request = route(app, FakeRequest(POST, baseUrl + "registered")
//      .withJsonBody(Json.toJson(entity))
//      .withHeaders(AUTHORIZATION -> "Token")
//    ).get
//
//    assert(status(request) equals OK)
//  }
//
//  test("login "+title) {
//      val request = route(app, FakeRequest(POST, baseUrl + "login")
//        .withJsonBody(Json.toJson(entity))
//        .withHeaders(AUTHORIZATION -> "Token")
//      ).get
//
//      assert(status(request) equals OK)
//    }
//
//  test("get "+title) {
//      val request = route(app, FakeRequest(POST, baseUrl + "user/" + entity.email)
//        .withJsonBody(Json.toJson(entity))
//        .withHeaders(AUTHORIZATION -> "Token")
//      ).get
//
//      assert(status(request) equals OK)
//  //    assert(contentAsString(request) equals Json.toJson(true).toString() )
//    }
//
//
//}
