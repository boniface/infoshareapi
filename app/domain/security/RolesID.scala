package domain.security

import javax.inject.Singleton

@Singleton
object RolesID {
  def READER: String = "READER"

  def MODERATOR: String = "MODERATOR"

  def CONTENT_CREATOR: String = "CONTENT_CREATOR"

  def EDITOR: String = "EDITOR"

  def PUBLISHER: String = "PUBLISHER"

  def SITE_ADMIN: String = "SITE_ADMINISTRATOR"

  def ADMIN: String = "SYSTEM_ADMINISTRATOR"
}
