package services.content

import java.time.{LocalDateTime =>Date}

import domain.content.EditedContent
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.Await
import scala.concurrent.duration._


class EditedContentServiceTest extends FunSuite with BeforeAndAfter {

  val e_contentService = EditedContentService
  var e_contentEntity, updateEntity: EditedContent = _
  var kwargs: Map[String,String] =  _

  before {
      e_contentEntity =  EditedContent(org="DUT", id ="1", dateCreated= Date.now(), creator="test@me.com", source="3", category ="3",
      title = "birth control", content = "we not animals", contentType = "Text/Image",
      status = "raw",  state ="active")
      kwargs = Map("org"->e_contentEntity.org, "id"->e_contentEntity.id)

  }

  test("create edited content"){
    val resp = Await.result(e_contentService.save(e_contentEntity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("get e_content By Id"){
    val resp = Await.result(e_contentService.getContentById(kwargs), 2.minutes)

    assert(resp.head.id == e_contentEntity.id)
    assert(resp.head.org == e_contentEntity.org)
    assert(resp.head.creator == e_contentEntity.creator)
    assert(resp.head.source == e_contentEntity.source)
    assert(resp.head.title == e_contentEntity.title)
    assert(resp.head.category == e_contentEntity.category)
    assert(resp.head.content == e_contentEntity.content)
    assert(resp.head.status == e_contentEntity.status)
    assert(resp.head.state == e_contentEntity.state)
    assert(resp.head.contentType == e_contentEntity.contentType)

  }
  test("update e_content"){
    updateEntity = e_contentEntity.copy(contentType ="1")
    val update  = Await.result(e_contentService.save(updateEntity), 2.minutes)
    val resp = Await.result(e_contentService.getContentById(kwargs), 2.minutes)

    assert(update.isExhausted)
    assert(resp.head.contentType != e_contentEntity.contentType)
    assert(resp.head.contentType == updateEntity.contentType)

  }

  test("getAll org content"){
    val result  = Await.result(e_contentService.save(e_contentEntity.copy(id="2")), 2.minutes)
    val resp = Await.result(e_contentService.getAllContents(kwargs("org")), 2.minutes)

    assert(resp.size > 1)
    assert(result.isExhausted)
  }

  test("get org content by range"){ // TODO: fixme
    val resp = Await.result(e_contentService.getPaginatedContents(kwargs("org"), 2), 2.minutes)
    assert(resp.isEmpty)
  }


}
