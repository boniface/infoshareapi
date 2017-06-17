package repositories.users.tables

import java.time.{LocalDateTime => Date}
import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8._
import com.outworkers.phantom.streams._
import domain.users.User

import scala.concurrent.Future


abstract class UserTable extends Table[UserTable,User]  {

  object org extends StringColumn with PartitionKey

  object email extends StringColumn with PrimaryKey

  object firstName extends StringColumn

  object lastName extends StringColumn

  object middleName extends OptionalStringColumn

  object screenName extends OptionalStringColumn

  object password extends StringColumn

  object state extends StringColumn

  object date extends Col[Date]
}


abstract class UserTableImpl extends UserTable with RootConnector {
  override lazy val tableName = "users"

  def save(user: User): Future[ResultSet] = {
    insert
      .value(_.org, user.org)
      .value(_.email, user.email)
      .value(_.firstName, user.firstName)
      .value(_.lastName, user.lastName)
      .value(_.middleName, user.middleName)
      .value(_.screenName, user.screenName)
      .value(_.password, user.password)
      .value(_.state, user.state)
      .value(_.date, user.date)
      .future()
  }

  def getUsers(org: String): Future[Seq[User]] = {
    select.where(_.org eqs org).fetchEnumerator() run Iteratee.collect()
  }
  def getUser(org:String, email: String): Future[Option[User]] = {
    select.where(_.org eqs org).and(_.email eqs email).one()
  }

}

abstract class PersonTable extends Table[PersonTable, User]{

  object email extends StringColumn with PartitionKey

  object org extends StringColumn with PrimaryKey

  object firstName extends StringColumn

  object lastName extends StringColumn

  object middleName extends OptionalStringColumn

  object screenName extends OptionalStringColumn

  object password extends StringColumn

  object state extends StringColumn

  object date extends Col[Date]
}

abstract class  PersonTableImpl extends PersonTable with RootConnector {

  override lazy val tableName = "persons"

  def save(user: User): Future[ResultSet] = {
    insert
      .value(_.org, user.org)
      .value(_.email, user.email)
      .value(_.firstName, user.firstName)
      .value(_.lastName, user.lastName)
      .value(_.middleName, user.middleName)
      .value(_.screenName, user.screenName)
      .value(_.password, user.password)
      .value(_.state, user.state)
      .value(_.date, user.date)
      .future()
  }

    def getUserByEmail(email: String): Future[Seq[User]] = {
      select.where(_.email eqs email).fetchEnumerator() run Iteratee.collect()
    }

}

