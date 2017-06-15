package services.content

import domain.content.Media
import java.time.{LocalDateTime =>Date}
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.Await
import scala.concurrent.duration._


class MediaServiceTest extends FunSuite with BeforeAndAfter{

  val service = MediaService
  var entity, updateEntity: Media = _

  before{
    entity = Media(contentId = "1", id = "1", description = "birth control",url = "https:me.com/jpg",
      mime = ".jpg",date = Date.now(),state = "Active")
  }

  test("Create media"){
    val resp = Await.result(service.save(entity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("Get media BY_ID"){
    val resp = Await.result(service.getById(Map("contentId"->entity.contentId,"id"-> entity.id)), 2.minutes)
    assert(resp.head.id == entity.id)
    assert(resp.head.contentId == entity.contentId)
    assert(resp.head.url == entity.url)
    assert(resp.head.mime == entity.mime)
    assert(resp.head.description == entity.description)
  }

  test("Update media"){
    updateEntity = entity.copy(state = "INACTIVE")
    val update = Await.result(service.save(updateEntity), 2.minutes)
    assert(update.isExhausted)

    val resp = Await.result(service.getById(Map("contentId"->updateEntity.contentId,"id"-> entity.id)), 2.minutes)
    assert(resp.head.state == updateEntity.state)
    assert(resp.head.state != entity.state)
  }

  test("Get All media"){
    val result = Await.result(service.save(entity.copy(id = "2")), 2.minutes)
    val resp = Await.result(service.getAll(entity.contentId), 2.minutes)

    assert(resp.size > 1)
    assert(result.isExhausted)

  }

}
