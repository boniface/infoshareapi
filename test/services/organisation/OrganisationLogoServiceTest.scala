package services.organisation

import java.time.LocalDateTime

import domain.organisation.OrganisationLogo
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.Await
import scala.concurrent.duration._


class OrganisationLogoServiceTest extends FunSuite with BeforeAndAfter {

  val service = OrganisationLogoService
  var entity, updateEntity: OrganisationLogo = _
  var kwargs: Map[String,String] = _

  before{
    entity = OrganisationLogo(org = "cput",id="3",url="http://www.google.com/e.jpg",size = Some("512MB"),
      description = "cput logo",mime = ".jpg",LocalDateTime.now())
    kwargs = Map("org"->entity.org,"id"->entity.id)

  }

  test("create OrganisationLogo"){
    val resp = Await.result(service.save(entity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("get OrganisationLogo by id"){
    val resp = Await.result(service.getById(kwargs), 2.minutes)
    assert(resp.head.id == entity.id)
    assert(resp.head.org == entity.org)
    assert(resp.head.size == entity.size)
  }

  test("update OrganisationLogo"){
    updateEntity = entity.copy(mime=".png",url = "https://www.cput.ac.za/cput.png")
    val update = Await.result(service.save(updateEntity), 2.minutes)
    val resp = Await.result(service.getById(kwargs), 2.minutes)

    assert(update.isExhausted)
    assert(resp.head.mime == updateEntity.mime)
    assert(resp.head.url == updateEntity.url)
    assert(resp.head.mime != entity.mime)
    assert(resp.head.url != entity.url)
  }

  test("get all OrganisationLogo"){
    val result = Await.result(service.save(entity.copy(id = "4")), 2.minutes)
    val resp = Await.result(service.getAll(kwargs("org")), 2.minutes)
    assert(result.isExhausted)
    assert(resp.size > 1)
  }


}
