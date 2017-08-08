package repositories.votes.tables

import java.time.LocalDateTime

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8._
import com.outworkers.phantom.streams._
import domain.votes.VoteUp

import scala.concurrent.Future

/**
  *
  */
abstract class VoteUpTable extends Table[VoteUpTable, VoteUp] {

  object siteId extends StringColumn with PartitionKey

  object itemId extends StringColumn with PartitionKey

  object ipAddress extends StringColumn with PrimaryKey

  object itemOwnerId extends StringColumn

  object date extends Col[LocalDateTime]

}

abstract class  VoteUpTableImpl extends VoteUpTable with RootConnector {

  override lazy val tableName = "upvotes"


  def save(vote: VoteUp): Future[ResultSet] = {
    insert
      .value(_.siteId, vote.siteId)
      .value(_.itemId, vote.itemId)
      .value(_.ipAddress, vote.ipAddress)
      .value(_.itemOwnerId, vote.itemOwnerId)
      .value(_.date, vote.date)
      .future()
  }

  def getVoteId(siteId: String, itemId: String, ipAddress: String): Future[Option[VoteUp]] = {
    select
      .where(_.siteId eqs siteId)
      .and(_.itemId eqs itemId)
      .and(_.ipAddress eqs ipAddress)
      .one()
  }

  def getVotes(siteId: String, itemId: String): Future[Seq[VoteUp]] = {
    select
      .where(_.siteId eqs siteId)
      .and(_.itemId eqs itemId)
      .fetchEnumerator() run Iteratee.collect()
  }

  def deleteVote(siteId: String, itemId: String, ipAddress: String): Future[ResultSet] = {
    delete
      .where(_.siteId eqs siteId)
      .and(_.itemId eqs itemId)
      .and(_.ipAddress eqs ipAddress)
      .future()
  }
}

abstract class UserUpVotesTable extends Table[UserUpVotesTable, VoteUp] {

  object siteId extends StringColumn with PartitionKey

  object itemOwnerId extends StringColumn with  PartitionKey

  object itemId extends StringColumn with PrimaryKey

  object ipAddress extends StringColumn with PrimaryKey

  object date extends Col[LocalDateTime]
}

abstract class  UserUpVotesTableImpl extends UserUpVotesTable with RootConnector {

  override lazy val tableName = "userupvotes"

  def save(vote: VoteUp): Future[ResultSet] = {
    insert
      .value(_.siteId, vote.siteId)
      .value(_.itemId, vote.itemId)
      .value(_.ipAddress, vote.ipAddress)
      .value(_.itemOwnerId, vote.itemOwnerId)
      .value(_.date, vote.date)
      .future()
  }

  def getUserVotes(siteId:String,itemOwnerId: String): Future[Seq[VoteUp]] = {
    select
      .where(_.siteId eqs siteId)
      .and(_.itemOwnerId eqs itemOwnerId)
      .fetchEnumerator() run Iteratee.collect()
  }

  def deleteVote(siteId:String,itemOwnerId:String,itemId: String, ipAddress: String): Future[ResultSet] = {
    delete
      .where(_.siteId eqs siteId)
      .and(_.itemOwnerId eqs itemOwnerId)
      .and(_.itemId eqs itemId)
      .and(_.ipAddress eqs ipAddress)
      .future()
  }
}

