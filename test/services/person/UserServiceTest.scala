package services.person

import domain.person.User
import org.joda.time.DateTime
import org.scalatest.FunSuite

import scala.concurrent.Await
import scala.concurrent.duration._

class UserServiceTest extends FunSuite{
   val entity = User("CPUT","test@test.com","First Name","Last Name",None,Some("CODER"),"test123","ACTIVE",new DateTime() )
  val updatedEntity = entity.copy(state = "INACTIVE")
  val service = UserService

  test("Create USER"){
    val result = Await.result(service.save(entity),2 minutes)
    assert(result.isExhausted)


  }

  test{"Read  USER "}{
    val result = Await.result(service.getUser("CPUT","test@test.com"),2 minutes)
    assert(result.head.state=="ACTIVE")

  }

  test("Upadte USER"){
    val update = Await.result(service.save(updatedEntity),2 minutes)
    val result = Await.result(service.getUserByEmail("test@test.com"),2 minutes)
    assert(result.head.state=="INACTIVE")
  }






}

