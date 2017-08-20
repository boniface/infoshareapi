package services.content

import domain.content.Source
import org.scalatest.{BeforeAndAfter, FunSuite}
import util.factories

import scala.concurrent.Await
import scala.concurrent.duration._

class SourceServiceTest extends FunSuite with BeforeAndAfter {

  val service = SourceService
  var entity, updateEntity: Source = _

  before {
    entity = factories.getSource
  }

  test("Create Source") {
    val resp = Await.result(service.save(entity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("Get Source BY_ID") {
    val resp = Await.result(service.getById(Map("org" -> entity.org, "id" -> entity.id)), 2.minutes)
    assert(resp.get equals entity)
  }

  test("Update Source") {
    updateEntity = entity.copy(org = "UCT")
    val update = Await.result(service.save(updateEntity), 2.minutes)
    val resp = Await.result(service.getById(Map("org" -> updateEntity.org, "id" -> updateEntity.id)), 2.minutes)

    assert(update.isExhausted)
    assert(resp.get != entity)
    assert(resp.get equals updateEntity)
  }

  test("Get All Source") {
    val result = Await.result(service.save(entity.copy(id = "2")), 2.minutes)
    val resp = Await.result(service.getAll(entity.org), 2.minutes)

    assert(resp.nonEmpty)
    assert(result.isExhausted)
  }

}
