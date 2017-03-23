package conf.security

import play.api.libs.json.JsValue
import play.api.mvc.{AnyContent, MultipartFormData, Request}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.reflect.io.File

/**
  * Created by hashcode on 2017/02/18.
  */
object TokenCheck {

  def getTokenForUpload(request: Request[MultipartFormData[File]]) = {

    getTokenValue(request.headers.get("Authorization"))
  }


  def getTokenfromParam(request: Request[AnyContent]) = {
    getTokenValue(request.headers.get("Authorization"))
  }


  def getToken(request: Request[JsValue]) = {
    getTokenValue(request.headers.get("Authorization"))

  }


  def getTokenValue(token: Option[String]): Future[LogInStatus] = {
    val tokenValue = token match {
      case Some(t) => t
      case None => ""
    }
    check(tokenValue) map (tkn => {
      if (tkn) LogInStatus("VALID")
      else throw TokenFailExcerption("Error")
    })
  }

  private def check(token: String) = {
    Future {
      token.equalsIgnoreCase("Token")
    }
  }
}

