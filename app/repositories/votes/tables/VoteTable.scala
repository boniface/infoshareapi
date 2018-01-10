package repositories.votes.tables

import java.time.LocalDateTime

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8._
import com.outworkers.phantom.streams._
import domain.votes.Vote

import scala.concurrent.Future



abstract  class VoteTable extends Table[VoteTable, Vote] {

  object siteId extends StringColumn with PartitionKey

  object itemId extends StringColumn with PartitionKey

  object ipAddress extends StringColumn with PrimaryKey

  object itemOwnerId extends StringColumn

  object date extends Col[LocalDateTime]

  object status extends StringColumn with Index

}

abstract class VoteTableImpl extends VoteTable with RootConnector {
  override lazy val tableName = "votes"


  def save(vote: Vote): Future[ResultSet] = {
    insert
      .value(_.siteId, vote.siteId)
      .value(_.itemId, vote.itemId)
      .value(_.ipAddress, vote.ipAddress)
      .value(_.itemOwnerId, vote.itemOwnerId)
      .value(_.date, vote.date)
      .value(_.status, vote.status)
      .future()
  }

  def getVoteId(entity: Map[String,String]): Future[Option[Vote]] = {
    println(entity)
    select
      .where(_.siteId eqs entity("siteId"))
      .and(_.ipAddress eqs entity("ipAddress"))
      .and(_.itemId eqs entity("itemId"))
      .one()
  }

  def getAllVotes(siteId: String, itemId: String): Future[Seq[Vote]] = {
    select
      .where(_.siteId eqs siteId)
      .and(_.itemId eqs itemId)
      .fetchEnumerator() run Iteratee.collect()
  }

  def getVotesByState(entity: Map[String,String]): Future[Seq[Vote]] = {
    select
      .where(_.siteId eqs entity("siteId"))
      .and(_.itemId eqs entity("itemId"))
      .and(_.status eqs  entity("status"))
      .fetchEnumerator() run Iteratee.collect()
  }

  def deleteVote(entity: Vote): Future[ResultSet] = {
    delete
      .where(_.siteId eqs entity.siteId)
      .and(_.itemId eqs entity.itemId)
      .and(_.ipAddress eqs entity.ipAddress)
      .future()
  }
}

abstract class UserVotesTable extends Table[UserVotesTable, Vote] {

  object siteId extends StringColumn with PartitionKey

  object itemOwnerId extends StringColumn with PartitionKey

  object itemId extends StringColumn with PrimaryKey

  object ipAddress extends StringColumn with PrimaryKey

  object date extends Col[LocalDateTime]

  object status extends StringColumn with Index


}

abstract class UserVotesTableImpl extends UserVotesTable with RootConnector {
  override lazy val tableName = "uservotes"

  def save(vote: Vote): Future[ResultSet] = {
    insert
      .value(_.siteId, vote.siteId)
      .value(_.itemId, vote.itemId)
      .value(_.ipAddress, vote.ipAddress)
      .value(_.itemOwnerId, vote.itemOwnerId)
      .value(_.date, vote.date)
      .value(_.status, vote.status)
      .future()
  }


  def getAllUserVotes(siteId: String, itemOwnerId:String): Future[Seq[Vote]] = {
    select
      .where(_.siteId eqs siteId)
      .and(_.itemOwnerId eqs itemOwnerId)
      .fetchEnumerator() run Iteratee.collect()
  }

  def getUserVotesByState(entity: Map[String,String]): Future[Seq[Vote]] = {
    select
      .where(_.siteId eqs entity("siteId"))
      .and(_.itemOwnerId eqs entity("itemOwnerId"))
      .and(_.status eqs  entity("status"))
      .fetchEnumerator() run Iteratee.collect()
  }

  def deleteVote(entity: Vote): Future[ResultSet] = {
    delete
      .where(_.siteId eqs entity.siteId)
      .and(_.itemOwnerId eqs entity.itemOwnerId)
      .and(_.itemId eqs entity.itemId)
      .and(_.ipAddress eqs entity.ipAddress)
      .future()
  }

}

