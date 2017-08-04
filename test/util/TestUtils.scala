package util

import org.scalatestplus.play.FirefoxFactory
import play.api.test.Helpers.{AUTHORIZATION, USER_AGENT}

object TestUtils extends FirefoxFactory {
  def getHeaders: Seq[(String, String)] = {
    val token = "eyJraWQiOiJQVUJMSUNLRVkiLCJhbGciOiJFUzI1NiJ9.eyJpc3MiOiJIQVNIQ09ERS5aTSIsImF1ZCI6IlNJVEVVU0VSUyIsImV4cCI6MTUwMTc2OTEyNCwianRpIjoiLUdXTnNYQ1NEdndGSWR3dG1uLUFKdyIsImlhdCI6MTUwMTY4MjcyNCwibmJmIjoxNTAxNjgyNjA0LCJzdWIiOiJTaXRlIEFjY2VzcyIsImVtYWlsIjoidGVzdEB0ZXN0LmNvbSIsInJvbGUiOiJSRUFERVIiLCJvcmdDb2RlIjoiQ1BVVCIsImFnZW50IjoiJDJhJDEyJHZWT2pZY3ZuaW5TQWpYU20wVG5KLk9sU2FnWVRMZm1tVVRaWEJjaGhjdGtNMFc0b3ZocHRhIn0.8t7atuxUkx-vAHWr4Et9M8jcqe-K_iAxcKgslbsv051eyHf8YrIuQetyjW9pO2hraRxyJkbzzdttOhToo-Pe1Q"

      Map(AUTHORIZATION -> token, USER_AGENT -> "Win7 x64 chrome").toSeq
  }
}
