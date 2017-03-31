package repositories.person.tables

import com.outworkers.phantom.dsl._
import domain.person.PersonAddress


class PersonAddressTable extends CassandraTable[PersonAddressTable, PersonAddress] {

  object personId extends StringColumn(this) with PartitionKey
  object addressTypeId extends StringColumn(this) with PrimaryKey
  object id extends StringColumn(this) with PrimaryKey
  object description extends StringColumn(this)
  object postalCode extends StringColumn(this)
  object date extends DateColumn(this)
  object state extends StringColumn(this)

}

