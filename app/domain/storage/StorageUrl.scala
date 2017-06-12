package domain.storage

import play.api.libs.json.Json

case class StorageUrl(id: String, name: String, url: String)

object StorageUrl {
  implicit val storageFmt = Json.format[StorageUrl]
  def identity: StorageUrl = StorageUrl("", "", "")
}
