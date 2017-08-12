package services.content

import domain.content.Category
import org.scalatest.{BeforeAndAfter, FunSuite}
import util.factories

import scala.concurrent.Await
import scala.concurrent.duration._


class CategoryServiceTest extends FunSuite with BeforeAndAfter {

  val service = CategoryService
  var entity, updateEntity : Category = _

  before{
    entity = factories.getCategory
  }

  test("Create Category"){
    val resp  = Await.result(service.save(entity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("Get Category BY_ID"){
    val resp = Await.result(service.getById(entity.id), 2.minutes)
    assert(resp.get equals entity)
  }

  test("Update Category"){
    updateEntity = entity.copy(name = "Cancer Cure", description = "how to cure cancer")
    val update = Await.result(service.save(updateEntity), 2.minutes)
    val resp = Await.result(service.getById(updateEntity.id), 2.minutes)

    assert(update.isExhausted)
    assert(resp.get != entity)
    assert(resp.get equals updateEntity)
  }

  test("Get all Categories") {
    val result = Await.result(service.save(entity.copy(id = "3")), 2.minutes)
    val resp = Await.result(service.getAll, 2.minutes)
    assert(result.isExhausted)
    assert(resp.nonEmpty)
  }

}
