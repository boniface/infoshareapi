package repositories.organisation.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.organisation.OrganisationLogo

import scala.concurrent.Future


class OrganisationLogoTable extends CassandraTable[OrganisationLogoTable, OrganisationLogo] {

  object org extends StringColumn(this) with PartitionKey

  object id extends StringColumn(this) with PrimaryKey

  object url extends StringColumn(this)

  object description extends StringColumn(this)

  object size extends OptionalStringColumn(this)

  object mime extends StringColumn(this)

  object date extends DateColumn(this)

}

abstract class OrganisationLogoTableImpl extends OrganisationLogoTable with RootConnector {
  override lazy val tableName = "orgLogo"

  def save(dept: OrganisationLogo) = {
    insert
      .value(_.org, dept.org)
      .value(_.id, dept.id)
      .value(_.url, dept.url)
      .value(_.size, dept.size)
      .value(_.mime, dept.mime)
      .value(_.date, dept.date)
      .value(_.description, dept.description)
      .future()
  }

  def findDCompanyLogo(org: String, id: String): Future[Option[OrganisationLogo]] = {
    select.where(_.org eqs org).and(_.id eqs id).one()
  }

  def findCompanyLogos(org: String): Future[Seq[OrganisationLogo]] = {
    select.where(_.org eqs org) fetchEnumerator() run Iteratee.collect()
  }
}
