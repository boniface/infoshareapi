package controllers.location

import javax.inject.Inject

import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class LocationRouter @Inject()(addressTypeCtrl: AddressTypeCtrl,
                               locationTypeCtrl: LocationTypeCtrl,
                               contactTypeCtrl: ContactTypeCtrl)
    extends SimpleRouter {
  override def routes: Routes = {
    //address
    case GET(p"/addresstype/all") =>
      addressTypeCtrl.getAll
    case GET(p"/addresstype/$id") =>
      addressTypeCtrl.getById(id)
    case POST(p"/addresstype/create") =>
      addressTypeCtrl.create

    //location type
    case GET(p"/locationtype/all") =>
      locationTypeCtrl.getAll
    case GET(p"/locationtype/$id") =>
      locationTypeCtrl.getById(id)
    case POST(p"/locationtype/create") =>
      locationTypeCtrl.create

    //contact type
    case GET(p"/contacttype/all") =>
      contactTypeCtrl.getAll
    case GET(p"/contacttype/$id") =>
      contactTypeCtrl.getById(id)
    case POST(p"/contacttype/create") =>
      contactTypeCtrl.create

  }
}
