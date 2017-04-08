package services.person

import com.outworkers.phantom.dsl.ResultSet
import domain.person.Person
import repositories.person.PersonRepository

import scala.concurrent.Future


trait PersonService extends PersonRepository{

  def save(person: Person): Future[ResultSet] ={
    database.personTable.save(person)
  }
  def getPerson(org: String,id: String): Future[Option[Person]] = {
    database.personTable.getPerson(org,id)
  }

  def getPeople(org:String): Future[Seq[Person]] = {
    database.personTable.getPeople(org)
  }
}
