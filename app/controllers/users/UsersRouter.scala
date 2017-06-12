package controllers.users

import javax.inject.Inject

import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class UsersRouter @Inject()(userCreationRouter: UserCreationController,
                            validUserController: ValidUserController)
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

    // Valid Users
    case POST(p"/valid/create") =>
      validUserController.create
    case GET(p"/valid/user/$userId") =>
      validUserController.isUserValid(userId)
    case GET(p"/valid/events/$userId") =>
      validUserController.getValidUserEvents(userId)
    case GET(p"/valid/users") =>
      validUserController.getValidUsers

  }
}
