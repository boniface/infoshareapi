package util

import org.scalatestplus.play.FirefoxFactory
import play.api.test.Helpers.{AUTHORIZATION, USER_AGENT}

object TestUtils extends FirefoxFactory {
  def getHeaders: Seq[(String, String)] = {
    val token = "eyJraWQiOiJQVUJMSUNLRVkiLCJhbGciOiJFUzI1NiJ9.eyJpc3MiOiJIQVNIQ09ERS5aTSIsImF1ZCI6IlNJVEVVU0VSUyIsImV4cCI6MTUwMjQ0ODQxNSwianRpIjoiX0lxcWVRdGJDYWx6cGlrNGNSR01XdyIsImlhdCI6MTUwMjM2MjAxNSwibmJmIjoxNTAyMzYxODk1LCJzdWIiOiJTaXRlIEFjY2VzcyIsImVtYWlsIjoidGVzdEB0ZXN0LmNvbSIsInJvbGUiOiJSRUFERVIiLCJvcmdDb2RlIjoiQ1BVVCIsImFnZW50IjoiJDJhJDEyJDg2TDdNWk1wL044V2tYY3FWNGNUYi4zczVtSmFRZUhRMWdKWG8yUnFJQU1ya0FoMGRFRHlpIn0.J1WvHdX793G0KGZGH82Q95rCSoXk8ghUyfR96-33J-FTjppIIjt1egrbnbY1U2FkWPusUuKyLg0CC0TM0geffw"
      Map(AUTHORIZATION -> token, USER_AGENT -> "Win7 x64 chrome").toSeq
  }
}
