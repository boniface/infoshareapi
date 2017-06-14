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
    case POST(p"/gender/create") =>
      genderCtrl.create

    //race
    case POST(p"/race/create") =>
      raceCtrl.create

    //role
    case POST(p"/role/create") =>
      roleCtrl.create

    //Language
    case POST(p"/language/create") =>
      languageCtrl.create

    //language proficiency
    case POST(p"/languageproficiency/create") =>
      langProCtrl.create

  }
}
