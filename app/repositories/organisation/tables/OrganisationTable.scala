package repositories.organisation.tables

import java.time.LocalDateTime
import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8._
import com.outworkers.phantom.streams._
import domain.organisation.Organisation

import scala.concurrent.Future


abstract class OrganisationTable extends Table[OrganisationTable, Organisation] {

  object id extends StringColumn with PartitionKey

  object name extends StringColumn

  object details extends MapColumn[String, String]

  object adminAttached extends StringColumn

  object date extends Col[LocalDateTime]

  object state extends StringColumn

}

abstract class OrganisationTableImpl extends OrganisationTable with RootConnector {
  override lazy val tableName = "organisation"

  def save(company: Organisation) = {
    insert
      .value(_.id, company.id)
      .value(_.name, company.name)
      .value(_.details, company.details)
      .value(_.adminAttached, company.adminAttached)
      .value(_.date, company.date)
      .value(_.state, company.state)
      .future()
  }

  def getById(id: String) = {
    select.where(_.id eqs id).one()
  }

  def getAll: Future[Seq[Organisation]] = {
    select.fetchEnumerator() run Iteratee.collect()
  }

}
