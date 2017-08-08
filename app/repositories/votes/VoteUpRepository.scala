package repositories.votes

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.votes.tables.{UserUpVotesTableImpl, VoteUpTableImpl}


class VoteUpDatabase(override val connector: KeySpaceDef) extends Database[VoteUpDatabase](connector) {

  object voteUpTable extends VoteUpTableImpl with connector.Connector

  object userUpVotesTable extends UserUpVotesTableImpl with connector.Connector

}

object VoteUpDatabase extends VoteUpDatabase(DataConnection.connector)

trait VoteUpRepository {
  def database = VoteUpDatabase
}


