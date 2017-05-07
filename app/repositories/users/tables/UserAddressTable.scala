package repositories.users.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.users.UserAddress

import scala.concurrent.Future



sealed class UserAddressTable extends CassandraTable[UserAddressTable, UserAddress] {

  object emailId extends StringColumn(this) with PartitionKey
  object id extends StringColumn(this) with PrimaryKey
  object addressTypeId extends StringColumn(this)
  object description extends StringColumn(this)
  object postalCode extends StringColumn(this)
  object date extends DateColumn(this)
  object state extends StringColumn(this)

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

  def findById(map: Map[String,String]): Future[Option[UserAddress]] = {
    select.where(_.emailId eqs map("emailId")).and(_.id eqs map("id")).one()
  }

  def findAllAddress(emailId: String): Future[Seq[UserAddress]] = {
    select.where(_.emailId eqs emailId).fetchEnumerator() run Iteratee.collect()
  }
}
