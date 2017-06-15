package controllers.demographics

import javax.inject.Inject

import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class DemographicsRouter @Inject()(genderCtrl: GenderCtrl,
                                   raceCtrl: RaceCtrl,
                                   roleCtrl: RoleCtrl,
                                   languageCtrl: LanguageCtrl,
                                   langProCtrl: LanguageProficiencyCtrl)
    extends SimpleRouter {
  override def routes: Routes = {

    //Gender
    case GET(p"/gender/all") =>
      genderCtrl.getAll
    case POST(p"/gender/delete/$id") =>
      genderCtrl.delete(id)
    case GET(p"/gender/$id") =>
      genderCtrl.getById(id)
    case POST(p"/gender/create") =>
      genderCtrl.create

    //race
    case GET(p"/race/all") =>
      raceCtrl.getAll
    case POST(p"/race/delete/$id") =>
      raceCtrl.delete(id)
    case GET(p"/race/$id") =>
      raceCtrl.getById(id)
    case POST(p"/race/create") =>
      raceCtrl.create

    //role
    case GET(p"/role/all") =>
      roleCtrl.getAll
    case GET(p"/role/$id") =>
      roleCtrl.getById(id)
    case POST(p"/role/create") =>
      roleCtrl.create

    //Language
    case GET(p"/language/all") =>
      languageCtrl.getAll
    case POST(p"/language/delete/$id") =>
      languageCtrl.delete(id)
    case GET(p"/language/$id") =>
      languageCtrl.getById(id)
    case POST(p"/language/create") =>
      languageCtrl.create

    //language proficiency
    case GET(p"/languageproficiency/all") =>
      langProCtrl.getAll
    case POST(p"/languageproficiency/delete/$id") =>
      langProCtrl.delete(id)
    case GET(p"/languageproficiency/$id") =>
      langProCtrl.getById(id)
    case POST(p"/languageproficiency/create") =>
      langProCtrl.create

  }
}
