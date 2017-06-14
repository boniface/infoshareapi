package controllers.content

import javax.inject.Inject

import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class ContentRouter @Inject()(contentTypeCtrl: ContentTypeCtrl,
                              categoryCtrl: CategoryCtrl,
                              rawContentCtrl: RawContentCtrl,
                              publishedContentCtrl: PublishedContentCtrl,
                              editedContentCtrl: EditedContentCtrl,
                              sourceCtrl: SourceCtrl,
                              mediaCtrl: MediaCtrl)
    extends SimpleRouter {
  override def routes: Routes = {
    //Category
    case POST(p"/category/create") =>
      categoryCtrl.create
    case GET(p"/category/$id") =>
      categoryCtrl.getById(id)
    case GET(p"/categoryies") =>
      categoryCtrl.getAll

    // contentType
    case POST(p"/contenttype/create") =>
      contentTypeCtrl.create
    case GET(p"/contenttype/$id") =>
      contentTypeCtrl.getById(id)
    case GET(p"/contenttypes") =>
      contentTypeCtrl.getAll

    // Raw content
    case GET(p"/raw/all/$org") =>
      rawContentCtrl.getAll(org)
    case GET(p"/raw/$org/$id") =>
      rawContentCtrl.getById(org, id)
//    case GET(p"/raw/range/$org/${int(start_value)}") =>
//      rawContentCtrl.getPaginated(org, start_value)
    case POST(p"/raw/create") =>
      rawContentCtrl.create

    // Edited content
    case GET(p"/edited/all/$org") =>
      editedContentCtrl.getAll(org)
    case GET(p"/edited/$org/$id") =>
      editedContentCtrl.getById(org, id)
//    case GET(p"/edited/range/$org/${int(start_value)}") =>
//      editedContentCtrl.getPaginated(org, start_value)
    case POST(p"/edited/create") =>
      editedContentCtrl.create

    // published content
    case GET(p"/published/all/$org") =>
      publishedContentCtrl.getAll(org)
    case GET(p"/published/$org/$id") =>
      publishedContentCtrl.getById(org, id)
//    case GET(p"/published/range/$org/${int(start_value)}") =>
//      publishedContentCtrl.getPaginated(org, start_value)
    case POST(p"/published/create") =>
      publishedContentCtrl.create

    // Media content
    case GET(p"/media/all/$org") =>
      mediaCtrl.getAll(org)
    case GET(p"/media/$content_Id/$id") =>
      sourceCtrl.getById(content_Id, id)
    case POST(p"/media/create") =>
      mediaCtrl.create

    // source content
    case GET(p"/source/all/$org") =>
      sourceCtrl.getAll(org)
    case GET(p"/source/$org/$id") =>
      sourceCtrl.getById(org, id)
    case POST(p"/source/create") =>
      sourceCtrl.create
  }
}
