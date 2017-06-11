package services.content

import java.time.{LocalDateTime =>Date}

import domain.content.RawContent
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.Await
import scala.concurrent.duration._


class RawContentServiceTest extends FunSuite with BeforeAndAfter {

  val r_contentService = RawContentService
  var r_contentEntity, updateEntity: RawContent = _
  var kwargs: Map[String,String] =  _

  before {
      r_contentEntity =  RawContent(org="DUT", id ="1", dateCreated= Date.now(), creator="test@me.com", source="2", category ="2",
      title = "birth control", content = "we not animals", contentType = "Text/Image",
      status = "raw",  state ="active")
      kwargs = Map("org"->r_contentEntity.org, "id"->r_contentEntity.id)

  }

  test("create raw content"){
    val resp = Await.result(r_contentService.save(r_contentEntity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("get r_content By Id"){
    val resp = Await.result(r_contentService.getContentById(kwargs), 2.minutes)

    assert(resp.head.id == r_contentEntity.id)
    assert(resp.head.org == r_contentEntity.org)
    assert(resp.head.creator == r_contentEntity.creator)
    assert(resp.head.source == r_contentEntity.source)
    assert(resp.head.title == r_contentEntity.title)
    assert(resp.head.category == r_contentEntity.category)
    assert(resp.head.content == r_contentEntity.content)
    assert(resp.head.status == r_contentEntity.status)
    assert(resp.head.state == r_contentEntity.state)
    assert(resp.head.contentType == r_contentEntity.contentType)

  }
  test("update r_content"){
    updateEntity = r_contentEntity.copy(contentType ="1")
    val update  = Await.result(r_contentService.save(updateEntity), 2.minutes)
    val resp = Await.result(r_contentService.getContentById(kwargs), 2.minutes)

    assert(update.isExhausted)
    assert(resp.head.contentType != r_contentEntity.contentType)
    assert(resp.head.contentType == updateEntity.contentType)

  }

  test("getAll org content"){
    val result  = Await.result(r_contentService.save(r_contentEntity.copy(id="2")), 2.minutes)
    val resp = Await.result(r_contentService.getAllContents(kwargs("org")), 2.minutes)

    assert(resp.size > 1)
    assert(result.isExhausted)
  }

  test("get org content by range"){ // TODO: fixme
    val resp = Await.result(r_contentService.getPaginatedContents(kwargs("org"), 2), 2.minutes)
    assert(resp.isEmpty)
  }


}
