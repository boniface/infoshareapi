//package controllers.organisation
//
//import javax.inject.Singleton
//
//import conf.security.{TokenCheck, TokenFailExcerption}
//import domain.organisation.Location
//import play.api.libs.json._
//import play.api.mvc._
//import services..LocationService
//
//import scala.concurrent.ExecutionContext.Implicits.global
//
//@Singleton
//class LocationCtrl extends InjectedController {
//  val service = LocationService
//
//  def create = Action.async(parse.json) { request =>
//    val entity = Json.fromJson[Location ](request.body).get
//    val response = for {
//      _ <- TokenCheck.getToken(request)
//      results <- service.save(entity)
//    } yield results
//    response.map(_ => Ok(Json.toJson(entity))).recover {
//      case _: TokenFailExcerption => Unauthorized
//      case _: Exception => InternalServerError
//    }
//  }
//
//  def getById(id: String) = Action.async {
//    implicit request: Request[AnyContent] =>
//      val resp = for {
//        _ <- TokenCheck.getTokenfromParam(request)
//        results <- service.getLocById(id)
//      } yield results
//      resp.map(msg => Ok(Json.toJson(msg))).recover {
//        case _: TokenFailExcerption => Unauthorized
//        case _: Exception => InternalServerError
//      }
//  }
//
//  def getAll = Action.async { implicit request: Request[AnyContent] =>
//    val resp = for {
//      _ <- TokenCheck.getTokenfromParam(request)
//      results <- service.getAllLocactions
//    } yield results
//    resp.map(msg => Ok(Json.toJson(msg))).recover {
//      case _: TokenFailExcerption => Unauthorized
//      case _: Exception => InternalServerError
//    }
//  }
//}
