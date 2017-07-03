package util

import play.api.test.Helpers.{AUTHORIZATION, USER_AGENT}

object UtilTest {
  def getHeaders: Seq[(String, String)] = {
    Map(AUTHORIZATION -> "Token", USER_AGENT -> "Win7 x64 chrome").toSeq
  }
}
