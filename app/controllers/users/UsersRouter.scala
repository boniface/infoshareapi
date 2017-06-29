package controllers.users

import javax.inject.Inject

import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class UsersRouter @Inject()(userCreationRouter: UserCreationController,
                            validUserController: ValidUserController,
                            userAddressCtrl: UserAddressCtrl,
                            userRoleCtrl: UserRoleCtrl,
                            userLanguageCtrl: UserLanguageCtrl,
                            userImageCtrl: UserImageCtrl,
                            userDemographicsCtrl: UserDemographicsCtrl,
                            userContactCtrl: UserContactCtrl,
                            userController: UserController)
    extends SimpleRouter {
  override def routes: Routes = {

    // user address
    case POST(p"/address/create") =>
      userAddressCtrl.create
    case GET(p"/address/all/$emailId") =>
      userAddressCtrl.getAll(emailId)
    case GET(p"/address/$emailId/$id") =>
      userAddressCtrl.getById(emailId, id)

    // user contact
    case POST(p"/contact/create") =>
      userContactCtrl.create
    case GET(p"/contact/all/$emailId") =>
      userContactCtrl.getAll(emailId)
    case GET(p"/contact/$emailId/$id") =>
      userContactCtrl.getById(emailId, id)

    // user
    case POST(p"/user/create") =>
      userController.create
    case GET(p"/user/org/$org") =>
      userController.getSiteUsers(org)
    case GET(p"/user/$org/$email") =>
      userController.getUserByEmail(org, email)
    case GET(p"/user/$email") =>
      userController.getUserAccounts(email)

    //user creation
    case POST(p"/usercreation/create/$roleId") =>
      userCreationRouter.createUser(roleId)
    case POST(p"/usercreation/register") =>
      userCreationRouter.registerUser
    case POST(p"/usercreation/update") =>
      userCreationRouter.updateUser
    case POST(p"/usercreation/registered") =>
      userCreationRouter.isRegistered
    case POST(p"/usercreation/login") =>
      userCreationRouter.login
    case GET(p"/usercreation/user/$email") =>
      userCreationRouter.getUser(email)

    // user demographics
    case POST(p"/demographics/create") =>
      userDemographicsCtrl.create
    case GET(p"/demographics/all/$emailId") =>
      userDemographicsCtrl.getAll(emailId)
    case GET(p"/demographics/$emailId/$id") =>
      userDemographicsCtrl.getById(emailId, id)

    // user images
    case POST(p"/image/create") =>
      userImageCtrl.create
    case GET(p"/image/user/$org/$emailId") =>
      userImageCtrl.getAlluserImages(org, emailId)
    case GET(p"/image/$org/$emailId/$id") =>
      userImageCtrl.getById(org, emailId, id)
    case GET(p"/image/org/$org") =>
      userImageCtrl.getAllOrgImages(org)

    // user language
    case POST(p"/language/create") =>
      userLanguageCtrl.create
    case GET(p"/language/all/$emailId") =>
      userLanguageCtrl.getAll(emailId)
    case GET(p"/language/$emailId/$id") =>
      userLanguageCtrl.getById(emailId, id)

    // user role
    case POST(p"/role/create") =>
      userRoleCtrl.create
    case GET(p"/role/all/$siteId/$emailId") =>
      userRoleCtrl.getAll(siteId,emailId)
    case GET(p"/role/$siteId/$emailId") =>
      userRoleCtrl.getById(emailId, emailId)
    case GET(p"/role/delete/$siteId/$emailId") =>
      userRoleCtrl.delete(emailId, emailId)

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
