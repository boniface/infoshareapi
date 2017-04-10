package repositories.util

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.util.tables.MailTableImpl


/**
  * Created by hashcode on 2017/01/29.
  */
class MailDatabase(override val connector: KeySpaceDef) extends Database[MailDatabase](connector) {

  object mailTable extends MailTableImpl with connector.Connector

}

object MailDatabase extends MailDatabase(DataConnection.connector)

trait MailRepository {

  def database = MailDatabase
}


