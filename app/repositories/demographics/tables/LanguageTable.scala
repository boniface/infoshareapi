package repositories.demographics.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.demographics.Language

import scala.concurrent.Future

abstract class LanguageTable extends Table[LanguageTable, Language] {
  object id extends StringColumn with PartitionKey

  object name extends StringColumn

  object state extends StringColumn

}

abstract class LanguageTableImpl extends LanguageTable with RootConnector {
  override lazy val tableName = "language"

  def save(language: Language): Future[ResultSet] = {
    insert
      .value(_.id, language.id)
      .value(_.name, language.name)
      .value(_.state, language.state)
      .future()
  }

  def findById(id: String): Future[Option[Language]] = {
    select.where(_.id eqs id).one()
  }
  def findAll: Future[Seq[Language]] = {
    select.fetchEnumerator() run Iteratee.collect()
  }

  def deleteById(id: String): Future[ResultSet] = {
    delete.where(_.id eqs id).future()
  }
}
