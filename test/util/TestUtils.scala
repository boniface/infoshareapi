package util

import org.scalatestplus.play.FirefoxFactory
import play.api.test.Helpers.{AUTHORIZATION, USER_AGENT}

object TestUtils extends FirefoxFactory {
  def getHeaders: Seq[(String, String)] = {
    val token = "eyJraWQiOiJQVUJMSUNLRVkiLCJhbGciOiJFUzI1NiJ9.eyJpc3MiOiJIQVNIQ09ERS5aTSIsImF1ZCI6IlNJVEVVU0VSUyIsImV4cCI6MTUwMTA4MDQ0MywianRpIjoiNmRfd1d6Y0tneFdjdFVHRFZUWFdiQSIsImlhdCI6MTUwMDk5NDA0MywibmJmIjoxNTAwOTkzOTIzLCJzdWIiOiJTaXRlIEFjY2VzcyIsImVtYWlsIjoidGVzdEB0ZXN0LmNvbSIsInJvbGUiOiJSRUFERVIiLCJvcmdDb2RlIjoiQ1BVVCIsImFnZW50IjoiJDJhJDEyJFhGazdZWlJ3UkhMYmdZbnJmNTFCUS5Xc3UybWFWNnBKM1UvQ3lncS8xSjJBWlk1dFlxRGZlIn0.rvfxJstpGb4Fh5ER4ITUfAYVnjZ6KMoSKlZaUN7q3h0Pw3scjoIIMdUYQaGxXgVra9ln0ziGtqko7_rkksoW4w"
      Map(AUTHORIZATION -> token, USER_AGENT -> "Win7 x64 chrome").toSeq
  }
}
