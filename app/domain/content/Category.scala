package domain.content

import play.api.libs.json.Json

/**

id          :unique id/pk of that category
name        :name of that category
description :full description of what the category is about
*/

case class Category(id: String,
                    name: String,
                    description: String
                   )

object Category {
  implicit val categoryFmt = Json.format[Category]
}
