package services.users.Impl

import domain.users.User
import services.security.AuthenticationService
import services.users.UserCreationMessageService
import views.html.email.new_login_details

/**
  * Created by hashcode on 6/24/17.
  */
class UserCreationMessageServiceImpl extends UserCreationMessageService{

  def accountCreatedMessage(user:User): (String, User)={
    val password = AuthenticationService.apply.generateRandomPassword()
    val createdUser = user.copy(password=AuthenticationService.apply.getHashedPassword(password))
    val message = new_login_details.render(createdUser)
    (message.toString(),createdUser)
  }

  def customUserMessage(user: User, message:String):(String, User)={

    val msg = "<html>" +
      "<body>" +
      "<h2><u>The Message Content</u></h2>" +
      "Dear " + user.screenName + " " + user.lastName + ",<p/>" + message + "</body></html>"

    (msg, user)
  }

}
