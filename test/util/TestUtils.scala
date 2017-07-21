package util

import org.scalatestplus.play.FirefoxFactory
import play.api.test.Helpers.{AUTHORIZATION, USER_AGENT}

object TestUtils extends FirefoxFactory {
  def getHeaders: Seq[(String, String)] = {
    Map(AUTHORIZATION -> "Token", USER_AGENT -> "Win7 x64 chrome").toSeq
  }
}
