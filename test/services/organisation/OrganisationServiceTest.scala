package services.organisation

import domain.organisation.Organisation
import org.scalatest.{BeforeAndAfter, FunSuite}
import util.factories

import scala.concurrent.Await
import scala.concurrent.duration._


class OrganisationServiceTest extends FunSuite with BeforeAndAfter {

  val service = OrganisationService
  var entity, updateEntity: Organisation = _

  before{
    entity = factories.getOrganisation
  }

  test("create Organisation"){
    val resp = Await.result(service.save(entity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("get Organisation by id"){
    val resp = Await.result(service.getById(entity.id), 2.minutes)
    assert(resp.get equals entity)
  }

  test("update Organisation"){
    updateEntity = entity.copy(name="DUT")
    val update = Await.result(service.save(updateEntity), 2.minutes)
    val resp = Await.result(service.getById(updateEntity.id), 2.minutes)

    assert(update.isExhausted)
    assert(resp.get != entity)
    assert(resp.get equals updateEntity)
  }

  test("get all Organisation"){
    val result = Await.result(service.save(entity.copy(id = "2")), 2.minutes)
    val resp = Await.result(service.getAll, 2.minutes)

    assert(resp.nonEmpty)
    assert(result.isExhausted)
  }


}
