package repositories.person.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.person.UserLanguage

import scala.concurrent.Future


class UserLanguageTable extends CassandraTable[UserLanguageTable, UserLanguage] {

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
  override lazy val tableName = "personLang"

  def save(personLanguage: UserLanguage): Future[ResultSet] = {
    insert
      .value(_.userId, personLanguage.userId)
      .value(_.id, personLanguage.id)
      .value(_.languageId, personLanguage.languageId)
      .value(_.reading, personLanguage.reading)
      .value(_.speaking, personLanguage.speaking)
      .value(_.writing, personLanguage.writing)
      .value(_.date, personLanguage.date)
      .value(_.state, personLanguage.state)
      .future()
  }

  def findById(userId: String, id: String): Future[Option[UserLanguage]] = {
    select.where(_.userId eqs userId).and(_.id eqs id).one()
  }

  def findPersonLanguages(userId: String): Future[Seq[UserLanguage]] = {
    select.where(_.userId eqs userId).fetchEnumerator() run Iteratee.collect()
  }


}
