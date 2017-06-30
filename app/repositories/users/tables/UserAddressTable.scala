package repositories.users.tables

import java.time.LocalDateTime
import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8._
import com.outworkers.phantom.streams._
import domain.users.UserAddress

import scala.concurrent.Future



abstract class UserAddressTable extends Table[UserAddressTable, UserAddress] {

  object emailId extends StringColumn with PartitionKey
  
  object id extends StringColumn with PrimaryKey
  
  object addressTypeId extends StringColumn
  
  object description extends StringColumn
  
  object postalCode extends StringColumn
  
  object date extends Col[LocalDateTime]
  
  object state extends StringColumn

}
abstract class UserAddressTableImpl extends UserAddressTable with RootConnector{
  override lazy val tableName = "userAddre"

  def save(userAddr: UserAddress): Future[ResultSet] = {
    insert
      .value(_.emailId, userAddr.emailId)
      .value(_.id, userAddr.id)
      .value(_.addressTypeId, userAddr.addressTypeId)
      .value(_.description, userAddr.description)
      .value(_.postalCode, userAddr.postalCode)
      .value(_.date, userAddr.date)
      .value(_.state, userAddr.state)
      .future()
  }

  def getById(map: Map[String,String]): Future[Option[UserAddress]] = {
    select.where(_.emailId eqs map("emailId")).and(_.id eqs map("id")).one()
  }

  def getAll(emailId: String): Future[Seq[UserAddress]] = {
    select.where(_.emailId eqs emailId).fetchEnumerator() run Iteratee.collect()
  }
}
