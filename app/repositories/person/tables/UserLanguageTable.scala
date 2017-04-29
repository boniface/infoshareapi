package repositories.person.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.person.UserLanguage

import scala.concurrent.Future


sealed class UserLanguageTable extends CassandraTable[UserLanguageTable, UserLanguage] {

  object userId extends StringColumn(this) with PartitionKey

  object id extends StringColumn(this) with PrimaryKey

  object languageId extends StringColumn(this)

  object reading extends StringColumn(this)

  object writing extends StringColumn(this)

  object speaking extends StringColumn(this)

  object date extends DateColumn(this)

  object state extends StringColumn(this)


}

abstract class UserLanguageTableImpl extends UserLanguageTable with RootConnector {
  override lazy val tableName = "userLangs"

  def save(userLanguage: UserLanguage): Future[ResultSet] = {
    insert
      .value(_.userId, userLanguage.userId)
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
    select.where(_.userId eqs map("userId")).and(_.id eqs map("id")).one()
  }


  def findUserLanguages(userId: String): Future[Seq[UserLanguage]] = {
    select.where(_.userId eqs userId).fetchEnumerator() run Iteratee.collect()
  }


}
