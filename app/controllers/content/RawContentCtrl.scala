package controllers.content

import javax.inject.Singleton

import conf.security.{TokenCheck, TokenFailExcerption}
import domain.content.RawContent
import play.api.libs.json._
import play.api.mvc._
import services.content.RawContentService
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class RawContentCtrl extends InjectedController {
  val service = RawContentService

  def create = Action.async(parse.json) { request =>
    val entity = Json.fromJson[RawContent](request.body).get
    val resp = for {
      _ <- TokenCheck.getToken(request)
      results <- service.save(entity)
    } yield results
    resp.map(_ => Ok(Json.toJson(entity))).recover {
      case _: TokenFailExcerption => Unauthorized
      case _: Exception => InternalServerError
    }
  }

  def getById(org: String, id: String) = Action.async {
    implicit request: Request[AnyContent] =>
      val perms: Map[String, String] = Map("org" -> org, "id" -> id)
      service.getContentById(perms) map (msg => Ok(Json.toJson(msg)))
  }

  def getPaginated(org: String, start_value: Int) = Action.async {
    implicit request: Request[AnyContent] =>
      service.getPaginatedContents(org, start_value) map (msg =>
        Ok(Json.toJson(msg.toSeq)))
  }

  def getAll(org: String) = Action.async {
    implicit request: Request[AnyContent] =>
      service.getAllContents(org) map (msg => Ok(Json.toJson(msg)))
  }

}
