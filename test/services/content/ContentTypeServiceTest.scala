package services.content

import domain.content.ContentType
import org.scalatest.{BeforeAndAfter, FunSuite}
import util.factories

import scala.concurrent.Await
import scala.concurrent.duration._


class ContentTypeServiceTest extends FunSuite with BeforeAndAfter {

  val service = ContentTypeService
  var entity, updateEntity: ContentType =  _

  before{
    entity = factories.getContentType
  }

  test("Create ContentType"){
    val resp = Await.result(service.save(entity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("Get contentTypeById"){
    val resp = Await.result(service.getById(entity.id), 2.minutes)
    assert(resp.get equals entity)
  }

  test("Update contentType"){
    updateEntity = entity.copy(name = "Image", description = "graphical content")
    val update = Await.result(service.save(updateEntity), 2.minutes)
    val resp = Await.result(service.getById(updateEntity.id), 2.minutes)

    assert(update.isExhausted)
    assert(resp.get != entity)
    assert(resp.get equals updateEntity)

  }

  test("Get All contentType"){
    val result = Await.result(service.save(entity.copy(id="2")), 2.minutes)
    val resp = Await.result(service.getAll, 2.minutes)

    assert(resp.nonEmpty)
    assert(result.isExhausted)
  }


}
