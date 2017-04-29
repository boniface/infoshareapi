package repositories.person.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.person.UserAddress

import scala.concurrent.Future



sealed class UserAddressTable extends CassandraTable[UserAddressTable, UserAddress] {

  object userId extends StringColumn(this) with PartitionKey
  object id extends StringColumn(this) with PrimaryKey
  object addressTypeId extends StringColumn(this)
  object description extends StringColumn(this)
  object postalCode extends StringColumn(this)
  object date extends DateColumn(this)
  object state extends StringColumn(this)

}
abstract class UserAddressTableImpl extends UserAddressTable with RootConnector{
  override lazy val tableName = "userAddr"

  def save(userAddr: UserAddress): Future[ResultSet] = {
    insert
      .value(_.userId, userAddr.userId)
      .value(_.id, userAddr.id)
      .value(_.addressTypeId, userAddr.addressTypeId)
      .value(_.description, userAddr.description)
      .value(_.postalCode, userAddr.postalCode)
      .value(_.date, userAddr.date)
      .value(_.state, userAddr.state)
      .future()
  }

  def findById(map: Map[String,String]): Future[Option[UserAddress]] = {
    select.where(_.userId eqs map("userId")).and(_.id eqs map("id")).one()
  }

  def findAllAddress(userId: String): Future[Seq[UserAddress]] = {
    select.where(_.userId eqs userId).fetchEnumerator() run Iteratee.collect()
  }
}
