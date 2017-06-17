package services.storage

import domain.storage.FileResults
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.Await
import scala.concurrent.duration._

class FileResultsServiceTest extends FunSuite with BeforeAndAfter {

  val service = FileResultsService
  var entity, updateEntity: FileResults = _

  before {
    entity = FileResults(id = "1",
                         url = "http://www.google.com/file.png",
                         size = Some("512MB"),
                         mime = ".png")
  }

  test("create file results") {
    val resp = Await.result(service.save(entity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("get file results by id") {
    val resp = Await.result(service.getById(entity.id), 2.minutes)
    assert(resp.head.id == entity.id)
    assert(resp.head.url == entity.url)
    assert(resp.head.size == entity.size)
    assert(resp.head.mime == entity.mime)
  }

  test("update file results") {
    updateEntity =
      entity.copy(url = "http://www.cput.ac.za/logo.jpg", mime = ".jpg")
    val update = Await.result(service.save(updateEntity), 2.minutes)
    val resp = Await.result(service.getById(updateEntity.id), 2.minutes)

    assert(update.isExhausted)
    assert(resp.head.url == updateEntity.url)
    assert(resp.head.url != entity.url)

    assert(resp.head.mime == updateEntity.mime)
    assert(resp.head.mime != entity.mime)
  }

  test("get all file results") {
    val result = Await.result(service.save(entity.copy(id = "2")), 2.minutes)
    val resp = Await.result(service.getAll, 2.minutes)
    assert(result.isExhausted)
    assert(resp.size > 1)
  }

}
