package controllers.util

import javax.inject.Inject

import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class UtilRouter @Inject()(roleController: RoleController)(
    keysController: KeysController)(
    mailSettingsController: MailSettingsController,
    itemsStatusCtrl: ItemsStatusCtrl)
    extends SimpleRouter {
  override def routes: Routes = {

    //ROLES
    case GET(p"/roles/all") =>
      roleController.getRoles
    case POST(p"/roles/create") =>
      roleController.create
    case GET(p"/roles/$id") =>
      roleController.getRoleById(id)

 //items status
    case POST(p"/roles/create") =>
      itemsStatusCtrl.create
    case GET(p"/roles/$id") =>
      itemsStatusCtrl.getById(id)

    //KEYS
    case GET(p"/keys/all") =>
      keysController.getKeys
    case POST(p"/keys/create") =>
      keysController.create
    case GET(p"/keys/$id") =>
      keysController.getKeyById(id)

    //MAIL
    case GET(p"/mail/all/$siteId") =>
      mailSettingsController.getSiteMailSettings(siteId)
    case POST(p"/mail/create") =>
      mailSettingsController.create
    case GET(p"/mail/$siteId/$id") =>
      mailSettingsController.getMailSettingById(siteId, id)

  }
}
