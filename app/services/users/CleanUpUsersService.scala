package services.users

import services.users.Impl.CleanUpUsersServiceImpl

/**
  * Created by hashcode on 6/24/17.
  */
trait CleanUpUsersService {
  def cleanUpInactiveUsers(siteId:String)

}

object CleanUpUsersService{
  def apply: CleanUpUsersService = new CleanUpUsersServiceImpl()
}
