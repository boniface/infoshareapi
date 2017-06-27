package services.users.Impl

import domain.users.User
import services.security.AuthenticationService
import services.users.UserCreationMessageService

/**
  * Created by hashcode on 6/24/17.
  */
class UserCreationMessageServiceImpl extends UserCreationMessageService{

  def accountCreatedMessage(user:User): (String, User)={
    val password = AuthenticationService.apply.generateRandomPassword()
    val message = "Your Login Details are Username: " + user.email + " And the Password: " + password + "" +
      "</p> You can access the Site  Provided to you By the Provider. " +
      "<b>PLEASE REMEMBER TO CHANGE YOUR PASSWORD</b><p/>" +
      "We are Sure your Superiors have told you that Great Powers Come with Great Responsibility"
    val createdUser = user.copy(password=AuthenticationService.apply.getHashedPassword(password))
    (message,createdUser)
  }

  def customUserMessage(user: User, message:String):(String, User)={

    val msg = "<html>" +
      "<body>" +
      "<h2><u>The Message Content</u></h2>" +
      "Dear " + user.screenName + " " + user.lastName + ",<p/>" + message + "</body></html>"

    (msg, user)
  }

}
