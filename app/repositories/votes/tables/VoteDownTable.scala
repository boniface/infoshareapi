package repositories.votes.tables

import java.time.LocalDateTime

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8._
import com.outworkers.phantom.streams._
import domain.votes.VoteDown

import scala.concurrent.Future

/**
  *
  * itemId:String,ipAddress:String,itemOwnerId:String
  *
  */

abstract  class VoteDownTable extends Table[VoteDownTable, VoteDown] {

  object siteId extends StringColumn with PartitionKey

  object itemId extends StringColumn with PartitionKey

  object ipAddress extends StringColumn with PrimaryKey

  object itemOwnerId extends StringColumn

  object date extends Col[LocalDateTime]

  override def fromRow(row: Row): VoteDown = {
    VoteDown(
      siteId(row),
      itemId(row),
      ipAddress(row),
      itemOwnerId(row),
      date(row)
    )
  }
}

abstract class VoteDownTableImpl extends VoteDownTable with RootConnector {
  override lazy val tableName = "downvotes"


  def save(vote: VoteDown): Future[ResultSet] = {
    insert
      .value(_.siteId, vote.siteId)
      .value(_.itemId, vote.itemId)
      .value(_.ipAddress, vote.ipAddress)
      .value(_.itemOwnerId, vote.itemOwnerId)
      .value(_.date, vote.date)
      .future()
  }

  def getVoteId(map: Map[String, String]): Future[Option[VoteDown]] = {
    select
      .where(_.siteId eqs map("siteId"))
      .and(_.itemId eqs map("itemId"))
      .and(_.ipAddress eqs map("ipAddress"))
      .one()
  }

  def getVotes(map: Map[String, String]): Future[Seq[VoteDown]] = {
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

abstract class UserDownVotesTable extends Table[UserDownVotesTable, VoteDown] {

  object siteId extends StringColumn with PartitionKey

  object itemOwnerId extends StringColumn with PartitionKey

  object itemId extends StringColumn with PrimaryKey

  object ipAddress extends StringColumn with PrimaryKey

  object date extends Col[LocalDateTime]


}

abstract class UserDownVotesTableImpl extends UserDownVotesTable with RootConnector {
  override lazy val tableName = "userdownvotes"

  def save(vote: VoteDown): Future[ResultSet] = {
    insert
      .value(_.siteId, vote.siteId)
      .value(_.itemId, vote.itemId)
      .value(_.ipAddress, vote.ipAddress)
      .value(_.itemOwnerId, vote.itemOwnerId)
      .value(_.date, vote.date)
      .future()
  }


  def getUserVotes(map: Map[String, String]): Future[Seq[VoteDown]] = {
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

