package services.users.Impl

import services.users.{CleanUpUsersService, UserService, ValidUserService}
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by hashcode on 6/24/17.
  */
class CleanUpUsersServiceImpl extends CleanUpUsersService {

  def cleanUpInactiveUsers = {

    val accounts = for {
      recentUsers <- UserService.getUsersAccountsOlderThanOneDay
      recentValidUser <- ValidUserService.getValidUsersInLast24hours
    } yield {
      val recentAccounts = recentUsers map (user => user.email)
      val validAccounts = recentValidUser map (user => user.userId)
      val accounts = recentAccounts.toSet.diff(validAccounts.toSet)
      for (email <- accounts) yield UserService.deleteUser(email)
    }

  }

}
