package controllers.person

import domain.person.Person
import play.api.libs.json.Json
import play.api.mvc._
import services.person.PersonService


class PersonController extends Controller{
  private[person] val createOrUpdate = Action.async(parse.json) {
    request =>
      val input = request.body
      val entity = Json.fromJson[Person](input).get
      val results = PersonService.save(entity)
      results.map(result =>
        Ok(Json.toJson(entity)))
  }

  private[person] def getOrganisationPeople: Nothing Nothing {
    request =>
    PeopleService.getPeople (id) map (result =>
    Ok (Json.toJson (result) ) )
  }

  private[person] def getPerson: Nothing Nothing {
    request =>
    PeopleService.getPerson (company, id) map (result =>
    Ok (Json.toJson (result) ) )
  }

  private[person] def getPersonByEmail: Nothing Nothing {
    request =>
    PeopleService.getPersonByEmail (email) map (result =>
    Ok (Json.toJson (result) ) )
  }
}
