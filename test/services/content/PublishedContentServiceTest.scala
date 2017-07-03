package services.content

import java.time.LocalDateTime

import domain.content.PublishedContent
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.Await
import scala.concurrent.duration._


class PublishedContentServiceTest extends FunSuite with BeforeAndAfter {

  val p_contentService = PublishedContentService
  var p_contentEntity, updateEntity: PublishedContent = _
  var kwargs: Map[String,String] =  _
  before {
    p_contentEntity =  PublishedContent(org="DUT", id ="1", dateCreated= LocalDateTime.now(),
      creator="test@me.com", source="1", category ="1",
      title = "birth control", content = "we not animals", contentType = "Text/Image",
      status = "plublished",  state ="active")

    kwargs = Map("org"->p_contentEntity.org,"id"->p_contentEntity.id)

  }

  test("create plublished content"){
    val resp = Await.result(p_contentService.save(p_contentEntity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("get p_content By Id"){
    val resp = Await.result(p_contentService.getById(kwargs), 2.minutes)

    assert(resp.head.id == p_contentEntity.id)
    assert(resp.head.org == p_contentEntity.org)
    assert(resp.head.creator == p_contentEntity.creator)
    assert(resp.head.source == p_contentEntity.source)
    assert(resp.head.title == p_contentEntity.title)
    assert(resp.head.category == p_contentEntity.category)
    assert(resp.head.content == p_contentEntity.content)
    assert(resp.head.status == p_contentEntity.status)
    assert(resp.head.state == p_contentEntity.state)
    assert(resp.head.contentType == p_contentEntity.contentType)

  }
  test("update p_content"){
    updateEntity = p_contentEntity.copy(contentType ="1")
    val update  = Await.result(p_contentService.save(updateEntity), 2.minutes)
    assert(update.isExhausted)

    val resp = Await.result(p_contentService.getById(kwargs), 2.minutes)

    assert(resp.head.contentType != p_contentEntity.contentType)
    assert(resp.head.contentType == updateEntity.contentType)

  }

  test("getAll org content"){
    val result  = Await.result(p_contentService.save(p_contentEntity.copy(id="2")), 2.minutes)

    val resp = Await.result(p_contentService.getAll(kwargs("org")), 2.minutes)
    assert(resp.size > 1)
    assert(result.isExhausted)
  }

  test("get org content by range"){
    // TODO: fixme
    val resp = Await.result(p_contentService.getPaginatedContents(kwargs("org"), 2), 2.minutes)
    assert(resp.isEmpty)
  }

}
