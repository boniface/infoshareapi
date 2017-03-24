package controllers.zones

import javax.inject.Inject

import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

/**
  * Created by hashcode on 2017/02/18.
  */
class ZoneRouter @Inject()
(feedController: FeedController)
(siteController: SiteController)
(zoneController: ZoneController)
(feedsProcessingController: FeedsProcessingController)extends SimpleRouter {
  override def routes: Routes = {
    //ZONES
    case GET(p"/all") =>
      zoneController.getAllZones
    case GET(p"/all/active") =>
      zoneController.getAllActiveZones
    case GET(p"/all/disabled") =>
      zoneController.getAllDisabledZones
    case GET(p"/$id") =>
      zoneController.getZoneById(id)
    case POST(p"/create") =>
      zoneController.createZone
    case POST(p"/update") =>
      zoneController.updateZone

      //Feeds
    case POST(p"/feed/create") =>
      feedController.create
    case POST(p"/feed/update") =>
      feedController.update
    case GET(p"/feeds/$zone") =>
      feedController.getZoneFeeds(zone)
    case GET(p"/feeds/site/$zone/$sitecode") =>
      feedController.getSiteFeeds(zone,sitecode)
    case GET(p"/feeds/site/get/$zone/$sitecode/$id") =>
      feedController.getFeedById(zone,sitecode,id)
    case GET(p"/feeds/site/delete/$zone/$sitecode/$id") =>
      feedController.deleteFeed(zone,sitecode,id)


    // Sites

    case GET(p"/site/$id/$site") =>
      siteController.getSiteById(id,site)
    case GET(p"/site/start/${int(start)}") =>
      siteController.getSiteByNumber(start)
    case GET(p"/site/all/$zone") =>
      siteController.getSiteByZones(zone)
    case POST(p"/site/create") =>
      siteController.create
    case POST(p"/site/update") =>
      siteController.update


    // Sites

    case GET(p"/process/feeds") =>
      feedsProcessingController.processFeeds
  }
}