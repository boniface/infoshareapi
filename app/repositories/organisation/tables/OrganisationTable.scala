package repositories.organisation.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.organisation.Organisation

import scala.concurrent.Future


class OrganisationTable extends CassandraTable[OrganisationTable, Organisation] {

  object id extends StringColumn(this) with PartitionKey

  object name extends StringColumn(this)

  object details extends MapColumn[String,String](this) // when this is highlighted its Intellij bug

  object adminAttached extends StringColumn(this)

  object date extends DateColumn(this)

  object state extends StringColumn(this)


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

  def updateCompany(company:Organisation):Future[ResultSet] ={
    insert
      .value(_.id, company.id)
      .value(_.name, company.name)
      .value(_.details, company.details)
      .value(_.adminAttached, company.adminAttached)
      .value(_.date, company.date)
      .value(_.state, company.state)
      .future()
  }

  def findById(id: String) = {
    select.where(_.id eqs id).one()
  }

  def findAll: Future[Seq[Organisation]] = {
    select.fetchEnumerator() run Iteratee.collect()
  }




}
