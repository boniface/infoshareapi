package repositories.organisation.tables

import java.time.LocalDateTime
import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8._
import com.outworkers.phantom.streams._
import domain.organisation.OrganisationLogo

import scala.concurrent.Future


abstract class OrganisationLogoTable extends Table[OrganisationLogoTable, OrganisationLogo] {

  object org extends StringColumn with PartitionKey

  object id extends StringColumn with PrimaryKey

  object url extends StringColumn

  object description extends StringColumn

  object size extends OptionalStringColumn

  object mime extends StringColumn

  object date extends Col[LocalDateTime]

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

  def getById(map: Map[String,String]): Future[Option[OrganisationLogo]] = {
    select.where(_.org eqs map("org")).and(_.id eqs map("id")).one()
  }

  def getAll(org: String): Future[Seq[OrganisationLogo]] = {
    select.where(_.org eqs org) fetchEnumerator () run Iteratee.collect()
  }
}
