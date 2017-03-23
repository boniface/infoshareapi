package repositories.errors.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.errors.ErrorReport

import scala.concurrent.Future


/**
  * Created by hashcode on 2016/08/14.
  */
class ErrorReportTable extends CassandraTable[ErrorReportTable, ErrorReport] {

  object zone extends StringColumn(this) with PartitionKey

  object date extends DateColumn(this) with PrimaryKey with ClusteringOrder with Descending

  object id extends StringColumn(this) with PrimaryKey with ClusteringOrder with Descending

  object site extends StringColumn(this)

  object message extends StringColumn(this)

}

abstract class  ErrorReportTableImpl extends ErrorReportTable with RootConnector {
  override lazy val tableName = "errors"

  def save(errors: ErrorReport): Future[ResultSet] = {
    insert
      .value(_.zone, errors.zone)
      .value(_.site, errors.site)
      .value(_.date, errors.date)
      .value(_.id, errors.id)
      .value(_.message, errors.message)
      .ttl(86400)
      .future()
  }

  def getErrorReport(zone: String): Future[Iterator[ErrorReport]] = {
    select.where(_.zone eqs zone).fetchEnumerator() run Iteratee.slice(0, 100)
  }
}

