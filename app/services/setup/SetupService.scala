package services.setup

import com.outworkers.phantom.dsl.ResultSet
import repositories.users._
import repositories.util._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by hashcode on 2017/04/21.
  */
object SetupService {

  def setup: Future[ResultSet] = {
    implicit val session = UserDatabase.session
    implicit val keyspace = UserDatabase.space

    for {
      //    util
      setup <- ItemStatusDatabase.itemStatusTable.create.ifNotExists().future()
      setup <- KeysDatabase.keysTable.create.ifNotExists().future()
      setup <- MailDatabase.mailTable.create.ifNotExists().future()
      setup <- TokenDatabase.tokenTable.create.ifNotExists().future()
      setup <- RoleDatabase.roleTable.create.ifNotExists().future()

      //     user
      setup <- UserAddressDatabase.userAddressTable.create.ifNotExists().future()
      setup <- UserContactDatabase.userContactTable.create.ifNotExists().future()
      setup <- UserDemographicsDatabase.userDemographicsTable.create.ifNotExists().future()
      setup <- UserLanguageDatabase.userLanguageTable.create.ifNotExists().future()
      setup <- UserImagesDatabase.userImagesTable.create.ifNotExists().future()
      setup <- UserDatabase.userTable.create.ifNotExists().future()
      setup <- UserDatabase.personTable.create.ifNotExists().future()
      setup <- UserRoleDatabase.userRoleTable.create.ifNotExists().future()
    }yield setup
  }

}
