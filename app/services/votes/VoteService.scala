package services.votes

import javax.inject.Singleton

import com.outworkers.phantom.dsl.ResultSet
import domain.votes.Vote
import repositories.votes.VoteRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


trait VoteService extends VoteRepository {

  def castVote(entity: Vote): Future[Boolean] = {
    val params = Map("siteId"->entity.siteId, "itemId"->entity.itemId, "ipAddress"->entity.ipAddress)

    getVoteId(params).flatMap { vote =>
      if (vote.nonEmpty && vote.get.status.equals(entity.status)) {
        deleteVote(entity)
        Future.successful(true)
      } else {
        save(entity)
        Future.successful(false)
      }
    }
  }

  private def save(obj: Vote): Future[ResultSet] = {
    for {
    entity <- database.votesTable.save(obj)
    entity <- database.userVotesTable.save(obj)
    } yield entity
  }

  private def deleteVote(obj: Vote): Future[ResultSet] = {
    for {
      resp <- database.votesTable.deleteVote(obj)
      resp <- database.userVotesTable.deleteVote(obj)
    } yield resp
  }

  def getVoteId(obj: Map[String,String]): Future[Option[Vote]] = {
    database.votesTable.getVoteId(obj)
  }

  def getAllVotes(siteId:String, itemId: String): Future[Seq[Vote]] = {
    database.votesTable.getAllVotes(siteId = siteId, itemId = itemId)
  }

  def getVotesByState(obj: Map[String,String]): Future[Seq[Vote]] = {
    database.votesTable.getVotesByState(obj)
  }

  def getAllUserVotes(siteId: String, itemOwnerId: String): Future[Seq[Vote]] = {
    database.userVotesTable.getAllUserVotes(siteId = siteId, itemOwnerId = itemOwnerId)
  }

  def getUserVotesByState(obj: Map[String,String]): Future[Seq[Vote]] = {
    database.userVotesTable.getUserVotesByState(obj)
  }

}

@Singleton
object VoteService extends VoteService with VoteRepository
