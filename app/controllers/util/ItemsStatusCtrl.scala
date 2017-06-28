package controllers.util

import javax.inject.{Inject, Singleton}

import domain.security.TokenFailException
import domain.util.ItemStatus
import play.api.libs.json._
import play.api.mvc._
import services.security.TokenCheckService
import services.util.ItemStatusService

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class ItemsStatusCtrl @Inject()(cc: ControllerComponents) extends AbstractController(cc)  {
  val service = ItemStatusService

  def create = Action.async(parse.json) { request =>
    val entity = Json.fromJson[ItemStatus](request.body).get
    val response = for {
      _ <- TokenCheckService.apply.getToken(request)
      results <- service.save(entity)
    } yield results
    response.map(_ => Ok(Json.toJson(entity))).recover {
      case _: TokenFailException => Unauthorized
      case _: Exception => InternalServerError
    }
  }

  def getById(id: String) = Action.async {
    implicit request: Request[AnyContent] =>
      val resp = for {
        _ <- TokenCheckService.apply.getTokenfromParam(request)
        results <- service.getStatus(id)
      } yield results
      resp.map(msg => Ok(Json.toJson(msg))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }

}
