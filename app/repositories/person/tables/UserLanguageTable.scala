package repositories.person.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.person.UserLanguage

import scala.concurrent.Future


class UserLanguageTable extends CassandraTable[UserLanguageTable, UserLanguage] {

  object personId extends StringColumn(this) with PartitionKey

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
      .value(_.personId, personLanguage.personId)
      .value(_.id, personLanguage.id)
      .value(_.languageId, personLanguage.languageId)
      .value(_.reading, personLanguage.reading)
      .value(_.speaking, personLanguage.speaking)
      .value(_.writing, personLanguage.writing)
      .value(_.date, personLanguage.date)
      .value(_.state, personLanguage.state)
      .future()
  }

  def findById(personId: String, id: String): Future[Option[UserLanguage]] = {
    select.where(_.personId eqs personId).and(_.id eqs id).one()
  }

  def findPersonLanguages(personId: String): Future[Seq[UserLanguage]] = {
    select.where(_.personId eqs personId).fetchEnumerator() run Iteratee.collect()
  }


}
