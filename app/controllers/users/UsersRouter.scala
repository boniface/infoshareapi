package controllers.users

import javax.inject.Inject

import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

/**
  * Created by hashcode on 2017/03/04.
  */
class UsersRouter @Inject()
(userCreationRouter: UserCreationController)
  extends SimpleRouter {
  override def routes: Routes = {
    //ROLES
    case POST(p"/create/$roleId") =>
      userCreationRouter.createUser(roleId)
    case POST(p"/user/register") =>
      userCreationRouter.registerUser
    case POST(p"/user/update") =>
      userCreationRouter.updateUser
    case POST(p"/user/registered") =>
      userCreationRouter.isRegistered
    case POST(p"/user/login") =>
      userCreationRouter.login
    case GET(p"/user/getuser/$email") =>
      userCreationRouter.getUser(email)

  }
}
