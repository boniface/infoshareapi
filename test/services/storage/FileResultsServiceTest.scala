package services.storage

import util.factories
import domain.storage.FileResults
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.Await
import scala.concurrent.duration._

class FileResultsServiceTest extends FunSuite with BeforeAndAfter {

  val service = FileResultsService
  var entity, updateEntity: FileResults = _

  before {
    entity = factories.getFileResults
  }

  test("create file results") {
    val resp = Await.result(service.save(entity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("get file results by id") {
    val resp = Await.result(service.getById(entity.id), 2.minutes)
    assert(resp.get equals entity)
  }

  test("update file results") {
    updateEntity = entity.copy(url = "http://www.cput.ac.za/logo.jpg", mime = ".jpg")
    val update = Await.result(service.save(updateEntity), 2.minutes)
    val resp = Await.result(service.getById(updateEntity.id), 2.minutes)

    assert(update.isExhausted)
    assert(resp.get != entity)
    assert(resp.get equals updateEntity)
  }

  test("get all file results") {
    val result = Await.result(service.save(entity.copy(id = "2")), 2.minutes)
    val resp = Await.result(service.getAll, 2.minutes)

    assert(resp.nonEmpty)
    assert(result.isExhausted)
  }

}
