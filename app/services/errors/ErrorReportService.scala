package services.errors

import com.outworkers.phantom.dsl.ResultSet
import domain.errors.ErrorReport
import repositories.errors.ErrorReportRepository

import scala.concurrent.Future

/**
  * Created by hashcode on 2017/01/29.
  */
trait ErrorReportService extends ErrorReportRepository {

  def save(errors: ErrorReport): Future[ResultSet] = {
    database.errorReportTable.save(errors)
  }

  def getErrorReport(zone: String): Future[Iterator[ErrorReport]] = {
    database.errorReportTable.getErrorReport(zone)
  }

}

object ErrorReportService extends ErrorReportService with ErrorReportRepository
