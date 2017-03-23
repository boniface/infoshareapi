package repositories.zones.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.zones.Site

import scala.concurrent.Future

/**
  * Created by hashcode on 2016/08/11.
  */
class SiteTable extends CassandraTable[SiteTable, Site] {

  object zone extends StringColumn(this) with PartitionKey

  object sitecode extends StringColumn(this) with PrimaryKey

  object url extends StringColumn(this)

  object name extends StringColumn(this)

  object logo extends StringColumn(this)

}

abstract class SiteTableImpl extends SiteTable with RootConnector {

  override lazy val tableName = "sites"

  def save(site: Site): Future[ResultSet] = {
    insert
      .value(_.zone, site.zone)
      .value(_.url, site.url)
      .value(_.logo, site.logo)
      .value(_.sitecode, site.sitecode)
      .value(_.name, site.name)
      .future()
  }

  def getSiteById(zone: String, sitecode: String): Future[Option[Site]] = {
    select.where(_.zone eqs zone).and(_.sitecode eqs sitecode).one()
  }

  def deleteSiteById(zone: String, sitecode: String): Future[ResultSet] = {
    delete.where(_.zone eqs zone).and(_.sitecode eqs sitecode).future()
  }

  def getSitesByNumber(start: Int, limit: Int): Future[Iterator[Site]] = {
    select.fetchEnumerator() run Iteratee.slice(start, limit)
  }

  def getSitesByZone(zone: String): Future[Seq[Site]] = {
    select.where(_.zone eqs zone).fetchEnumerator() run Iteratee.collect()
  }
}
