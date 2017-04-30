package repositories.users.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.users.UserLanguage

import scala.concurrent.Future


sealed class UserLanguageTable extends CassandraTable[UserLanguageTable, UserLanguage] {

  object emailId extends StringColumn(this) with PartitionKey

  object id extends StringColumn(this) with PrimaryKey

  object languageId extends StringColumn(this)

  object reading extends StringColumn(this)

  object writing extends StringColumn(this)

  object speaking extends StringColumn(this)

  object date extends DateColumn(this)

  object state extends StringColumn(this)


}

abstract class UserLanguageTableImpl extends UserLanguageTable with RootConnector {
  override lazy val tableName = "userLangua"

  def save(userLanguage: UserLanguage): Future[ResultSet] = {
    insert
      .value(_.emailId, userLanguage.emailId)
      .value(_.id, userLanguage.id)
      .value(_.languageId, userLanguage.languageId)
      .value(_.reading, userLanguage.reading)
      .value(_.speaking, userLanguage.speaking)
      .value(_.writing, userLanguage.writing)
      .value(_.date, userLanguage.date)
      .value(_.state, userLanguage.state)
      .future()
  }

  def findById(map: Map[String, String]): Future[Option[UserLanguage]] = {
    select.where(_.emailId eqs map("emailId")).and(_.id eqs map("id")).one()
  }


  def findUserLanguages(emailId: String): Future[Seq[UserLanguage]] = {
    select.where(_.emailId eqs emailId).fetchEnumerator() run Iteratee.collect()
  }


}
