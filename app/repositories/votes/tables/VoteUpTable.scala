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

  def getVoteId(map: Map[String, String]): Future[Option[VoteUp]] = {
    select
      .where(_.siteId eqs map("siteId"))
      .and(_.itemId eqs map("itemId"))
      .and(_.ipAddress eqs map("ipAddress"))
      .one()
  }

  def getVotes(map: Map[String, String]): Future[Seq[VoteUp]] = {
    select
      .where(_.siteId eqs map("siteId"))
      .and(_.itemId eqs map("itemId"))
      .fetchEnumerator() run Iteratee.collect()
  }

  def deleteVote(map: Map[String, String]): Future[ResultSet] = {
    delete
      .where(_.siteId eqs map("siteId"))
      .and(_.itemId eqs map("itemId"))
      .and(_.ipAddress eqs map("ipAddress"))
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

  def getUserVotes(map: Map[String, String]): Future[Seq[VoteUp]] = {
    select
      .where(_.siteId eqs map("siteId"))
      .and(_.itemOwnerId eqs map("itemOwnerId"))
      .fetchEnumerator() run Iteratee.collect()
  }

  def deleteVote(map: Map[String, String]): Future[ResultSet] = {
    delete
      .where(_.siteId eqs map("siteId"))
      .and(_.itemOwnerId eqs map("itemOwnerId"))
      .and(_.itemId eqs map("itemId"))
      .and(_.ipAddress eqs map("ipAddress"))
      .future()
  }
}

