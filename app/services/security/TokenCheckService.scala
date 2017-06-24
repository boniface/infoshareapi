package services.security

import domain.security.LogInStatus
import play.api.libs.json.JsValue
import play.api.mvc.{AnyContent, MultipartFormData, Request}
import services.security.Impl.TokenCheckServiceImpl

import scala.concurrent.Future
import scala.reflect.io.File

/**
  * Created by hashcode on 6/24/17.
  */
trait TokenCheckService{

  def getTokenForUpload(request: Request[MultipartFormData[File]]): Future[LogInStatus]

  def getTokenfromParam(request: Request[AnyContent]): Future[LogInStatus]

  def getToken(request: Request[JsValue]): Future[LogInStatus]

  def getTokenValue(token: String, agent:String): Future[LogInStatus]

}


object TokenCheckService {

  def apply: TokenCheckService = new TokenCheckServiceImpl()


}

