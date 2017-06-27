package services.content

import domain.content.ContentType
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.Await
import scala.concurrent.duration._


class ContentTypeServiceTest extends FunSuite with BeforeAndAfter {

  val contentTypeService = ContentTypeService
  var contentTypeEntity, updateEntity: ContentType =  _
  before{
    contentTypeEntity = ContentType(id = "1", name = "TEXT", description = "text content")
  }

  test("Create ContentType"){
    val resp = Await.result(contentTypeService.save(contentTypeEntity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("Get contentTypeById"){
    val resp = Await.result(contentTypeService.getById(contentTypeEntity.id), 2.minutes)
    assert(resp.head.id == contentTypeEntity.id)
    assert(resp.head.name == contentTypeEntity.name)
    assert(resp.head.description == contentTypeEntity.description)
  }

  test("Update contentType"){
    updateEntity = contentTypeEntity.copy(name = "Image", description = "graphical content")
    val update = Await.result(contentTypeService.save(updateEntity), 2.minutes)
    assert(update.isExhausted)
    val resp = Await.result(contentTypeService.getById(updateEntity.id), 2.minutes)

    assert(resp.head.name == updateEntity.name)
    assert(resp.head.description == updateEntity.description)

    assert(resp.head.name != contentTypeEntity.name)
    assert(resp.head.description != contentTypeEntity.description)
  }

  test("Get All contentType"){
    val result = Await.result(contentTypeService.save(contentTypeEntity.copy(id="2")), 2.minutes)
    val resp = Await.result(contentTypeService.getAll, 2.minutes)

    assert(resp.size > 1)
    assert(result.isExhausted)
  }


}
