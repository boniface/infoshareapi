package domain.users

import play.api.libs.json.Json

/**
  * Created by hashcode on 6/27/17.
  */
case class Login(email:String, sites:Set[String])

object Login {

  implicit val loginFmt = Json.format[Login]
  def identity: Login = Login("",Set())

}
