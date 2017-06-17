package conf.util

import javax.inject.Singleton

@Singleton
object Events {
  def VALIDATED = "VALIDATED"

  def LOGGEDIN = "LOGGEDIN"

  def LOGGEOUT = "LOGGEDOUT"

}
