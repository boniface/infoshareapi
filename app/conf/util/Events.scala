package conf.util

import javax.inject.Singleton

/**
  * Created by hashcode on 2017/06/11.
  */
@Singleton
object Events {
  def VALIDATED = "VALIDATED"
  def LOGGEDIN = "LOGGEDIN"
  def LOGGEOUT = "LOGGEDOUT"

}

