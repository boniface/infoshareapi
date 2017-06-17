package controllers.storage

import javax.inject.Inject

import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class StorageRouter @Inject()(storageUrlCtrl: StorageUrlCtrl,
                              fileResultsCtrl: FileResultsCtrl)
    extends SimpleRouter {
  override def routes: Routes = {
    //storage url
    case GET(p"/storageurl/all") =>
      storageUrlCtrl.getAll
    case GET(p"/storageurl/$id") =>
      storageUrlCtrl.getById(id)
    case POST(p"/storageurl/create") =>
      storageUrlCtrl.create

    //file result
    case GET(p"/fileresult/all") =>
      fileResultsCtrl.getAll
    case GET(p"/fileresult/$id") =>
      fileResultsCtrl.getById(id)
    case POST(p"/fileresult/create") =>
      fileResultsCtrl.create

  }
}
