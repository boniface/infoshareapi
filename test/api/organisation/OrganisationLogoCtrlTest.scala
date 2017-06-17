package api.organisation

import java.time.{LocalDateTime => Date}

import domain.organisation.OrganisationLogo
import org.scalatest.{BeforeAndAfter, FunSuite}
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._

class OrganisationLogoCtrlTest extends FunSuite with BeforeAndAfter with GuiceOneAppPerTest {

  /*
  * //organisation logo
    case GET(p"/organisationlogo/all/$org") =>
      organisationLogoCtrl.getAll(org)
    case GET(p"/organisationlogo/$org/$id") =>
      organisationLogoCtrl.getById(org, id)
    case POST(p"/organisationlogo/create") =>
      organisationLogoCtrl.create*/
  var entity, updateEntity : OrganisationLogo = _
  var baseUrl = "/org/organisationlogo/"
  val title = "organisation logo"

  before{
    entity = OrganisationLogo(org = "cput",id="3",url="http://www.google.com/e.jpg",size = Some("512MB"),
      description = "cput logo",mime = ".jpg",Date.now())
  }

  test("Create "+title){
    val request = route(app, FakeRequest(POST, baseUrl + "create")
      .withJsonBody(Json.toJson(entity))
      .withHeaders(AUTHORIZATION -> "Token")
    ).get

    assert(status(request) equals OK)
    assert(contentAsString(request) equals Json.toJson(entity).toString() )
  }

  test("update "+title){
    updateEntity = entity.copy(mime=".png",url = "https://www.cput.ac.za/cput.png")
    val request = route(app, FakeRequest(POST, baseUrl+"create")
      .withJsonBody(Json.toJson(updateEntity))
      .withHeaders(AUTHORIZATION -> "Token")
    ).get
    assert(status(request) equals OK)
    assert(contentAsString(request) != Json.toJson(entity).toString())
    assert(contentAsString(request) equals Json.toJson(updateEntity).toString())
  }

  test("get "+title+" by id"){
    val request = route(app, FakeRequest(GET, baseUrl+entity.org+"/"+entity.id)
      .withHeaders(AUTHORIZATION -> "Token")
    ).get
    assert(status(request) equals OK)
    assert(contentAsString(request) equals Json.toJson(updateEntity).toString())
  }

  test("get all "+title){
    val request = route(app, FakeRequest(GET, baseUrl+"all/"+entity.org)
      .withHeaders(AUTHORIZATION -> "Token")
    ).get
    assert(status(request) equals OK)
  }

}
