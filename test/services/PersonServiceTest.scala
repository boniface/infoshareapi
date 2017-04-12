package services

import domain.person._
import org.scalatestplus.play.PlaySpec
import services.person.PersonService

import scala.concurrent.ExecutionContext.Implicits.global


class PersonServiceTest extends PlaySpec{

  "PersonSrevice # getPerson" should {
    "find a person given an organisation and ID" in {

      val personRecord = Person("HBC", "2", "John", "Johhny",
        "john@example.com", "Doe", "password",
        true, false, true, true, "ACTIVE")

      val personService = PersonService
      personService.save(personRecord)

      val person = personService.getPerson(org = "HBC", id = "2")

      person mustBe personRecord
      person.map {  o => o match {
        case Some(x) => {
          assert(x.org === "HBC")
        }
      }
      }
      println(person)
      person.foreach(println)
    }
  }


  //  "PersonService #getById" should{
  //    "be true when the id exists" in{
  //
  //      val personAddress =  PersonAddress("Add1","123","1 Tenant Street","7925","ADT001", new Date(),"")
  //
  //      val personService = PersonAddressService
  //      personService.saveOrUpdate(personAddress)
  //      val addressType = personService.getValues("ADT001")
  //
  //      personAddress map {
  //        o => o match{
  //          case Some(x) =>{
  //            assert(x.description === "1 Tenant Street")
  //          }
  //        }
  //      }
  //    }
  //  }

}

