package repositories.demographics.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.demographics.LanguageProficiency

import scala.concurrent.Future

abstract class LanguageProficiencyTable extends Table[LanguageProficiencyTable, LanguageProficiency] {

  object id extends StringColumn with PartitionKey

  object name extends StringColumn

  object state extends StringColumn

}

abstract class LanguageProficiencyTableImpl extends LanguageProficiencyTable with RootConnector {
  override lazy val tableName = "languageProf"

  def save(langpr: LanguageProficiency): Future[ResultSet] = {
    insert
      .value(_.id, langpr.id)
      .value(_.name, langpr.name)
      .value(_.state, langpr.state)
      .future()
  }

  def findById(id: String): Future[Option[LanguageProficiency]] = {
    select.where(_.id eqs id).one()
  }
  def findAll: Future[Seq[LanguageProficiency]] = {
    select.fetchEnumerator() run Iteratee.collect()
  }

  def deleteById(id: String): Future[ResultSet] = {
    delete.where(_.id eqs id).future()
  }
}
