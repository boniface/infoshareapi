package services.votes

import javax.inject.Singleton

import com.outworkers.phantom.dsl.ResultSet
import domain.votes.VoteUp
import repositories.votes.VoteUpRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


trait VoteUpService extends VoteUpRepository {
  def save(obj: VoteUp): Future[ResultSet] = {
    for {
      entity <- database.voteUpTable.save(obj)
      entity <- database.userUpVotesTable.save(obj)
    } yield entity
  }

  def getVoteId(map: Map[String, String]): Future[Option[VoteUp]] = {
    database.voteUpTable.getVoteId(map)
  }

  def getVotes(map: Map[String, String]): Future[Seq[VoteUp]] = {
    database.voteUpTable.getVotes(map)
  }

  def getUserVotes(map: Map[String, String]): Future[Seq[VoteUp]] = {
    database.userUpVotesTable.getUserVotes(map)
  }

  def  deleteVote(map: Map[String, String]): Future[ResultSet] = {
    for{
      entity <- database.voteUpTable.deleteVote(map)
      entity <- database.userUpVotesTable.deleteVote(map)
    } yield entity
  }
}

@Singleton
object VoteUpService extends VoteUpService with VoteUpRepository
