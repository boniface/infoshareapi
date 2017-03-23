package domain.users

import org.joda.time.DateTime
import play.api.libs.json.Json

/**
  * Created by hashcode on 2016/08/12.
  */
case class User( siteId:String,
                 email: String,
                 screenName: String,
                 firstname:Option[String],
                 lastName:Option[String],
                 password: String,
                 state:String,
                 date:DateTime
               )

object User {
  implicit val userFmt = Json.format[User]
  def zero:User= User("","","",None,None,"","",new DateTime)
}
