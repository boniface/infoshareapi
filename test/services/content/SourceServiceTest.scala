package services.content

import domain.content.Source
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.Await
import scala.concurrent.duration._



class SourceServiceTest extends FunSuite with BeforeAndAfter{

  val sourceService = SourceService
  var sourceEntity, updateEntity: Source = _

  before{
    sourceEntity = Source(org="CPUT", id="1",name= "Mobile", description = "content from the mobile")
  }

  test("Create Source"){
    val resp = Await.result(sourceService.save(sourceEntity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("Get Source BY_ID"){
    val resp = Await.result(sourceService.getById(Map("org"->sourceEntity.org,"id"-> sourceEntity.id)), 2.minutes)
    assert(resp.head.id == sourceEntity.id)
    assert(resp.head.name == sourceEntity.name)
    assert(resp.head.org == sourceEntity.org)
    assert(resp.head.description == sourceEntity.description)
  }

  test("Update Source"){
    updateEntity = sourceEntity.copy(org = "UCT")
    val update = Await.result(sourceService.save(updateEntity), 2.minutes)
    assert(update.isExhausted)

    val resp = Await.result(sourceService.getById(Map("org"->updateEntity.org,"id"-> sourceEntity.id)), 2.minutes)
    assert(resp.head.org == updateEntity.org)
    assert(resp.head.org != sourceEntity.org)
  }

  test("Get All Source"){
    val result = Await.result(sourceService.save(sourceEntity.copy(id = "2")), 2.minutes)
    val resp = Await.result(sourceService.getAll(sourceEntity.org), 2.minutes)

    assert(resp.size > 1)
    assert(result.isExhausted)

  }

}
