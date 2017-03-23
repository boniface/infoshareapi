package repositories.zones.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.zones.Zone

import scala.concurrent.Future

/**
  * Created by hashcode on 2016/08/11.
  */
sealed class ZoneTable extends CassandraTable[ZoneTable, Zone] {

  object code extends StringColumn(this) with PartitionKey

  object name extends StringColumn(this)

  object status extends StringColumn(this)

  object logo extends StringColumn(this)

}

abstract class ZoneTableImpl extends ZoneTable with RootConnector {

  override lazy val tableName = "zones"

  def SaveOrUpdate(zone: Zone): Future[ResultSet] = {
    insert
      .value(_.code, zone.code)
      .value(_.name, zone.name)
      .value(_.status, zone.status)
      .value(_.logo, zone.logo)
      .future()
  }

  def getZoneById(code: String): Future[Option[Zone]] = {
    select.where(_.code eqs code).one()
  }

  def getAllZones: Future[Seq[Zone]] = {
    select.fetchEnumerator() run Iteratee.collect()
  }

  def deleteZoneById(code: String): Future[ResultSet] = {
    delete.where(_.code eqs code).future()
  }

}
