package services.content

import domain.content.Category
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.Await
import scala.concurrent.duration._


class CategoryServiceTest extends FunSuite with BeforeAndAfter{

  val categoryService = CategoryService
  var categoryEntity, updateEntity : Category = _

  before{
    categoryEntity = Category(id="1",name="HIV PREVENTION", description="how to prevent HIV")
  }

  test("Create Category"){
    val cat  = Await.result(categoryService.save(categoryEntity), 2.minutes)
    assert(cat.isExhausted)
  }

  test("Get Category BY_ID"){
    val resp = Await.result(categoryService.getCategoryById(categoryEntity.id), 2.minutes)
    assert(resp.head.id == categoryEntity.id)
    assert(resp.head.name == categoryEntity.name)
    assert(resp.head.description == categoryEntity.description)
  }

  test("Update Category"){
    updateEntity = categoryEntity.copy(name = "Cancer Cure", description = "how to cure cancer")
    val update = Await.result(categoryService.save(updateEntity), 2.minutes)
    assert(update.isExhausted)

    val resp = Await.result(categoryService.getCategoryById(updateEntity.id), 2.minutes)
    assert(resp.head.id == updateEntity.id)
    assert(resp.head.name == updateEntity.name)
    assert(resp.head.description == updateEntity.description)

    assert(resp.head.name != categoryEntity.name)
    assert(resp.head.description != categoryEntity.description)
  }

  test("Get all Categories") {
    val result = Await.result(categoryService.save(categoryEntity.copy(id = "3")), 2.minutes)

    val resp = Await.result(categoryService.getAllCategories, 2.minutes)
    assert(resp.size > 1)
    assert(result.isExhausted)
  }

}
