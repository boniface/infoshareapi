package services

import domain.person._
import org.scalatestplus.play.PlaySpec
import services.person.PersonService

import scala.concurrent.ExecutionContext.Implicits.global


class PersonServiceTest extends PlaySpec{

  "PersonService # getPerson" should{
    "find a person given an organisation and ID" in {

      val personRecord = Person("CPUT",
        "1",
        "John",
        "john@example.com",
        "jnr",
        "Doe" ,
        "thulebobakd",
        true,false,true,true, "ACTIVE")

      val personService = PersonService
      personService.save(personRecord)
      val response = personService.getPerson("CPUT", "1")

      response.toString must not endWith ("thule")
      response.foreach(ans => println(ans))
    }
  }



}
