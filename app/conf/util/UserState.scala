package conf.util

import javax.inject.Singleton


@Singleton
object UserState {
  def CONFIRMED="CONFIRMED"
  def UNCONFIRMED="UNCONFIRMED"
  def SITEADMIN="SITEADMIN"
  def ADMIN="ADMIN"
  def MODERATOR="MODERATOR"
}
