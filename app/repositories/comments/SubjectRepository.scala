package repositories.comments

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.comments.tables.SubjectTableImpl


class SubjectDatabase (override val connector: KeySpaceDef) extends Database[SubjectDatabase](connector) {
  object subjectTable extends SubjectTableImpl with connector.Connector
}

object SubjectDatabase extends SubjectDatabase(DataConnection.connector)

trait SubjectRepository  {
  def  database = SubjectDatabase
}

