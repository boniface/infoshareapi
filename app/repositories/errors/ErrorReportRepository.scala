package repositories.errors

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.errors.tables.ErrorReportTableImpl



/**
  * Created by hashcode on 2017/01/29.
  */
class ErrorReportDatabase (override val connector: KeySpaceDef) extends Database[ErrorReportDatabase](connector) {
  object errorReportTable extends ErrorReportTableImpl with connector.Connector
}

object ErrorReportDatabase extends ErrorReportDatabase(DataConnection.connector)

trait ErrorReportRepository  {
  def  database = ErrorReportDatabase
}



