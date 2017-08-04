package services.content

import conf.util.HashcodeKeys
import domain.content.Media
import org.scalatest.{BeforeAndAfter, FunSuite}
import util.factories

import scala.concurrent.Await
import scala.concurrent.duration._


class MediaServiceTest extends FunSuite with BeforeAndAfter{

  val service = MediaService
  var entity, updateEntity: Media = _

  before{
    entity = factories.getMedia
  }

  test("Create media"){
    val resp = Await.result(service.save(entity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("Get media BY_ID"){
    val resp = Await.result(service.getById(Map("contentId"->entity.contentId,"id"-> entity.id)), 2.minutes)
    assert(resp.get equals entity)
  }

  test("Update media"){
    updateEntity = entity.copy(state = HashcodeKeys.INACTIVE)
    val update = Await.result(service.save(updateEntity), 2.minutes)
    val resp = Await.result(service.getById(Map("contentId"->updateEntity.contentId,"id"-> updateEntity.id)), 2.minutes)

    assert(update.isExhausted)
    assert(resp.get != entity)
    assert(resp.get equals updateEntity)
  }

  test("Get All media"){
    val result = Await.result(service.save(entity.copy(id = "2")), 2.minutes)
    val resp = Await.result(service.getAll(entity.contentId), 2.minutes)

    assert(resp.size > 1)
    assert(result.isExhausted)
  }
}
