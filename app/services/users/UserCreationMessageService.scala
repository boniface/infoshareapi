package services.users

import domain.users.User
import services.users.Impl.UserCreationMessageServiceImpl

/**
  * Created by hashcode on 6/24/17.
  */
trait UserCreationMessageService {
  def accountCreatedMessage(user:User): (String, User)

  def customUserMessage(user: User, message:String):(String, User)

}
object UserCreationMessageService{

  def apply: UserCreationMessageService = new UserCreationMessageServiceImpl()
}
