package controllers.util

import javax.inject.Inject

import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

/**
  * Created by hashcode on 2017/03/04.
  */
class UtilRouter @Inject()
(roleController: RoleController)
(keysConroller: KeysConroller)
(mailSettingsController: MailSettingsController)
  extends SimpleRouter {
  override def routes: Routes = {

    //ROLES
    case GET(p"/roles/all") =>
      roleController.getRoles
    case POST(p"/roles/create") =>
      roleController.create
    case GET(p"/roles/$id") =>
      roleController.getRoleById(id)

    //KEYS
    case GET(p"/keys/all") =>
      keysConroller.getKeys
    case POST(p"/keys/create") =>
      keysConroller.create
    case GET(p"/keys/$id") =>
      keysConroller.getKeyById(id)

      //MAIL

    case GET(p"/mail/all/$siteId") =>
      mailSettingsController.getSiteMailSettings(siteId)
    case POST(p"/mail/create") =>
      mailSettingsController.create
    case GET(p"/mail/$siteId/$id") =>
      mailSettingsController.getMailSettingById(siteId,id)

  }
}
