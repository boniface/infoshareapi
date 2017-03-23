package domain.users

import org.joda.time.DateTime
import play.api.libs.json.Json


/**
  * Created by hashcode on 2016/10/19.
  */
case class UserStatus(userId:String,
                      date: DateTime,
                      status:String
                     )

object UserStatus{
  implicit val userFmt = Json.format[UserStatus]
  def zero:UserStatus = UserStatus("",new DateTime(),"")

}
