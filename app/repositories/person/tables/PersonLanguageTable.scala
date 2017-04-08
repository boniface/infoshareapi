package repositories.person.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.person.PersonLanguage

import scala.concurrent.Future


class PersonLanguageTable extends CassandraTable[PersonLanguageTable, PersonLanguage] {

  object personId extends StringColumn(this) with PartitionKey

  object id extends StringColumn(this) with PrimaryKey

  object languageId extends StringColumn(this)

  object reading extends StringColumn(this)

  object writing extends StringColumn(this)

  object speaking extends StringColumn(this)

  object date extends DateColumn(this)

  object state extends StringColumn(this)


}

abstract class PersonLanguageTableImpl extends PersonLanguageTable with RootConnector {
  override lazy val tableName = "personLang"

  def save(personLanguage: PersonLanguage): Future[ResultSet] = {
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

  def findById(personId: String, id: String): Future[Option[PersonLanguage]] = {
    select.where(_.personId eqs personId).and(_.id eqs id).one()
  }

  def findPersonLanguages(personId: String): Future[Seq[PersonLanguage]] = {
    select.where(_.personId eqs personId).fetchEnumerator() run Iteratee.collect()
  }


}
