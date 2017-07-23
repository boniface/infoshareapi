package util

import org.scalatestplus.play.FirefoxFactory
import play.api.test.Helpers.{AUTHORIZATION, USER_AGENT}

object TestUtils extends FirefoxFactory {
  def getHeaders: Seq[(String, String)] = {
    val token = "eyJraWQiOiJQVUJMSUNLRVkiLCJhbGciOiJFUzI1NiJ9.eyJpc3MiOiJIQVNIQ09ERS5aTSIsImF1ZCI6IlNJVEVVU0VSUyIsImV4cCI6MTUwMDgzMDgxMSwianRpIjoiUUlYVE1yR3c2NnpFYTFpTUdGOC1XdyIsImlhdCI6MTUwMDc0NDQxMSwibmJmIjoxNTAwNzQ0MjkxLCJzdWIiOiJTaXRlIEFjY2VzcyIsImVtYWlsIjoiYm9uaWZhY2VAa2FiYXNvLmNvbSIsInJvbGUiOiJSRUFERVIiLCJvcmdDb2RlIjoiQ1BVVCIsImFnZW50IjoiJDJhJDEyJDl0QjhvUkQzRGkzQVJubmJKQjVnTS4wT0ZsM0dIVDUwbkI5WDk0SU9rVnl3ZllCUGhWNncuIn0.Z0PAd0YyKN3_rm_AKiCwXfzKEkB9AApqrodtcqVLYZZ0Zqr2CHAGdErpT0FjVvCcVkSKvSbbn8tsbFnVufy8lg"

    Map(AUTHORIZATION -> token, USER_AGENT -> "Win7 x64 chrome").toSeq
  }
}
