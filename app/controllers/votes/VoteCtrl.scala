package controllers.votes

import javax.inject.{Inject, Singleton}

import domain.security.TokenFailException
import domain.votes.Vote
import play.api.libs.json.Json._
import play.api.mvc._
import services.security.TokenCheckService
import services.votes.VoteService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class VoteCtrl @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  private val service = VoteService

  def castVote = Action.async(parse.json) { request =>
    val entity = fromJson[Vote](request.body).get
    val response = for {
      _ <- TokenCheckService.apply.getUserToken(request)
      results <- service.castVote(entity)
    } yield results
    response.map(res => if(res) Ok(toJson(res)) else Created(toJson(entity)) ).recover {
      case _: TokenFailException => Unauthorized
      case _: Exception => InternalServerError
    }
  }

  def getVoteById(siteId: String, itemId: String, ipAddress: String) = Action.async {
    implicit request: Request[AnyContent] =>
      val params = Map("siteId"->siteId, "itemId"->itemId, "ipAddress"->ipAddress)
      val resp: Future[Option[Vote]] = for {
        _ <- TokenCheckService.apply.getUserToken(request)
        results <- service.getVoteId(params)
      } yield results
      resp.map(res => Ok(toJson(res))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }

  def getAllVotes(siteId: String, itemId: String) = Action.async {
    implicit request: Request[AnyContent] =>
      val resp = for {
        _ <- TokenCheckService.apply.getUserToken(request)
        results <- service.getAllVotes(siteId, itemId)
      } yield results
      resp.map(res => Ok(toJson(res))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }

  def getAllUserVotes(siteId: String, itemOwnerId: String) = Action.async {
    implicit request: Request[AnyContent] =>
      val resp = for {
        _ <- TokenCheckService.apply.getUserToken(request)
        results <- service.getAllUserVotes(siteId, itemOwnerId)
      } yield results
      resp.map(res => Ok(toJson(res))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }

  def getVotesByState(siteId: String, itemId: String, status: String) = Action.async {
    implicit request: Request[AnyContent] =>
      val params = Map("siteId"->siteId, "itemId"->itemId, "status"->status)
      val resp = for {
        _ <- TokenCheckService.apply.getUserToken(request)
        results <- service.getVotesByState(params)
      } yield results
      resp.map(res => Ok(toJson(res))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }

  def getUserVotesByState(siteId: String, itemOwnerId: String, status: String) = Action.async {
    implicit request: Request[AnyContent] =>
      val params = Map("siteId"->siteId, "itemOwnerId"->itemOwnerId, "status"->status)
      val resp = for {
        _ <- TokenCheckService.apply.getUserToken(request)
        results <- service.getUserVotesByState(params)
      } yield results
      resp.map(res => Ok(toJson(res))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }

}
