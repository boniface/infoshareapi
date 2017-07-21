package services.storage

import domain.storage.StorageUrl
import org.scalatest.{BeforeAndAfter, FunSuite}
import util.factories

import scala.concurrent.Await
import scala.concurrent.duration._

class StorageUrlServiceTest extends FunSuite with BeforeAndAfter {

  val service = StorageUrlService
  var entity, updateEntity: StorageUrl = _

  before {
    entity = factories.getStorageUrl
  }

  test("create storage") {
    val resp = Await.result(service.save(entity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("get storage by id") {
    val resp = Await.result(service.getLinkById(entity.id), 2.minutes)
    assert(resp.get equals entity)
  }

  test("update storage") {
    updateEntity = entity.copy(name = "profile")
    val update = Await.result(service.save(updateEntity), 2.minutes)
    val resp = Await.result(service.getLinkById(updateEntity.id), 2.minutes)

    assert(update.isExhausted)
    assert(resp.get != entity)
    assert(resp.get equals updateEntity)
  }

  test("get all links") {
    val result = Await.result(service.save(entity.copy(id = "2")), 2.minutes)
    val resp = Await.result(service.getAllLinks, 2.minutes)

    assert(resp.nonEmpty)
    assert(result.isExhausted)
  }
}
