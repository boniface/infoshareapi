package services.content

import domain.content.PublishedContent
import org.scalatest.{BeforeAndAfter, FunSuite}
import util.factories

import scala.concurrent.Await
import scala.concurrent.duration._


class PublishedContentServiceTest extends FunSuite with BeforeAndAfter {

  val service = PublishedContentService
  var entity, updateEntity: PublishedContent = _
  var kwargs: Map[String,String] =  _

  before {
    entity =  factories.getPublishedContent
    kwargs = Map("org"->entity.org,"id"->entity.id)
  }

  test("create published content"){
    val resp = Await.result(service.save(entity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("get published content By Id"){
    val resp = Await.result(service.getById(kwargs), 2.minutes)
    assert(resp.get == entity)
  }

  test("update published content"){
    updateEntity = entity.copy(contentTypeId ="1")
    val update  = Await.result(service.save(updateEntity), 2.minutes)
    val resp = Await.result(service.getById(kwargs), 2.minutes)

    assert(update.isExhausted)
    assert(resp.get != entity)
    assert(resp.get equals updateEntity)
  }

  test("getAll org content"){
    val result  = Await.result(service.save(entity.copy(id="2")), 2.minutes)
    val resp = Await.result(service.getAll(kwargs("org")), 2.minutes)

    assert(resp.nonEmpty)
    assert(result.isExhausted)
  }

  test("get org content by range"){ // TODO: fixme
    val resp = Await.result(service.getPaginatedContents(kwargs("org"), 2), 2.minutes)
    assert(resp.isEmpty)
  }

}
