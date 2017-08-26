package repositories.votes

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.votes.tables.{UserVotesTableImpl, VoteTableImpl}


class VoteDatabase(override val connector: KeySpaceDef) extends Database[VoteDatabase](connector) {

  object userVotesTable extends UserVotesTableImpl with connector.Connector

  object votesTable extends VoteTableImpl with connector.Connector

}

object VoteDatabase extends VoteDatabase(DataConnection.connector)

trait VoteRepository {
  def database = VoteDatabase
}
