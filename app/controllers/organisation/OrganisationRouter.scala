package controllers.organisation

import javax.inject.Inject

import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class OrganisationRouter @Inject()(locationCtrl: LocationCtrl,
                                   organisationCtrl: OrganisationCtrl,
                                   organisationLogoCtrl: OrganisationLogoCtrl)
    extends SimpleRouter {
  override def routes: Routes = {
    //location
    case GET(p"/location/all/$org") =>
      locationCtrl.getAll(org)
    case GET(p"/location/$org/$id") =>
      locationCtrl.getById(org, id)
    case POST(p"/location/delete/$org/$id") =>
      locationCtrl.delete(org, id)
    case POST(p"/location/create") =>
      locationCtrl.create

    //organisation logo
    case GET(p"/organisationlogo/all/$org") =>
      organisationLogoCtrl.getAll(org)
    case GET(p"/organisationlogo/$org/$id") =>
      organisationLogoCtrl.getById(org, id)
    case POST(p"/organisationlogo/create") =>
      organisationLogoCtrl.create

    //organisation
    case GET(p"/organisation/all") =>
      organisationCtrl.getAll
    case GET(p"/organisation/$id") =>
      organisationCtrl.getById(id)
    case POST(p"/organisation/create") =>
      organisationCtrl.create
  }
}
