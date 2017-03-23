package domain.zones

import play.api.libs.json.Json

/**
 * Created by hashcode on 2014/12/01.
 */
case class Site( zone:String,
                 sitecode:String,
                 url:String,
                 name:String,
                 logo:String
                 )

object Site {
  implicit val siteFmt = Json.format[Site]
}
