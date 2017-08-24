package services.votes

import javax.inject.Singleton

import com.outworkers.phantom.dsl.ResultSet
import domain.votes.VoteDown
import repositories.votes.VoteDownRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by hashcode
  */
trait VoteDownService extends VoteDownRepository {

  def save(obj: VoteDown): Future[ResultSet] = {
    for {
      entity <- database.votesDownTable.save(obj)
      entity <- database.userDownVotesTable.save(obj)
    } yield entity
  }

  def getVoteId(map: Map[String, String]): Future[Option[VoteDown]] = {
    database.votesDownTable.getVoteId(map)
  }

  def getVotes(map: Map[String, String]): Future[Seq[VoteDown]] = {
    database.votesDownTable.getVotes(map)
  }

  def getUserVotes(map: Map[String, String]): Future[Seq[VoteDown]] = {
    database.userDownVotesTable.getUserVotes(map)
  }

  def  deleteVote(map: Map[String, String]): Future[ResultSet] = {
    for{
      entity <- database.votesDownTable.deleteVote(map)
      entity <- database.userDownVotesTable.deleteVote(map)
    } yield entity
  }
}

@Singleton
object VoteDownService extends VoteDownService with VoteDownRepository
