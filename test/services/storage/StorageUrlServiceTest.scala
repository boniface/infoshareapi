package services.storage

import domain.storage.StorageUrl
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.Await
import scala.concurrent.duration._


class StorageUrlServiceTest extends FunSuite with BeforeAndAfter {

  val storageUrlService = StorageUrlService
  var storageEntity, updateEntity: StorageUrl = _

  before{
    storageEntity = StorageUrl(id = "1", name = "my profile", url = "http://cput.ac.za/logo.jpeg")
  }

  test("create storage"){
    val resp = Await.result(storageUrlService.save(storageEntity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("get storage by id"){
    val resp = Await.result(storageUrlService.getLinkById(storageEntity.id), 2.minutes)
    assert(resp.head.id == storageEntity.id)
    assert(resp.head.name == storageEntity.name)
    assert(resp.head.url == storageEntity.url)
  }

  test("update storage"){
    updateEntity = storageEntity.copy(name = "profile")
    val update = Await.result(storageUrlService.save(updateEntity), 2.minutes)
    val resp = Await.result(storageUrlService.getLinkById(storageEntity.id), 2.minutes)
    assert(update.isExhausted)
    assert(resp.head.name != storageEntity.name)
    assert(resp.head.name equals updateEntity.name)
  }

  test("get all links"){
    val result = Await.result(storageUrlService.save(storageEntity.copy(id = "2")), 2.minutes)
    val resp = Await.result(storageUrlService.getAllLinks, 2.minutes)

    assert(result.isExhausted)
    assert(resp.size > 1)
  }
}
