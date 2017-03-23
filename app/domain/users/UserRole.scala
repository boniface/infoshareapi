package domain.users

import org.joda.time.DateTime
import play.api.libs.json.Json

/**
  * Created by hashcode on 2016/08/12.
  */
case class UserRole( emailId: String,
                     date:DateTime,
                     roleId: String)

object UserRole {
  implicit val userroleFmt = Json.format[UserRole]
  def zero:UserRole = UserRole("",new DateTime(),"")
}
