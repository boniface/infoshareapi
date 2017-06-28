package controllers.syslog

import javax.inject.Inject

import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class SystemLogRouter @Inject()(syslogCtrl: SystemLogEventsCtrl)
    extends SimpleRouter {
  override def routes: Routes = {
    //System Log Events
    case GET(p"/systemlog/all/$siteId") =>
      syslogCtrl.getAll(siteId)
    case GET(p"/systemlog/$siteId/$id") =>
      syslogCtrl.getById(siteId, id)
    case POST(p"/systemlog/create") =>
      syslogCtrl.create
  }
}
