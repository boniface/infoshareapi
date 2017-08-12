package services.util


import java.time.LocalDateTime

import domain.util.Mail
import org.scalatest.FunSuite

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by hashcode on 2017/04/22.
  */
class MailServiceTest extends FunSuite{

//
//  val entity = Mail("CPUT","1","MAILL","mail.host.com","smtp.google.com","587","ACTIVE", LocalDateTime.now())
//  val updatedEntity = entity.copy(state = "INACTIVE")
//  val service = MailService
//
//  test("Create Mail Setting"){
//    val result = Await.result(service.save(entity),2 minutes)
//    assert(result.isExhausted)
//
//
//  }
//
//  test{"Read  Mail Setting"}{
//    val result = Await.result(service.getAllMailSettings("CPUT"),2 minutes)
//    assert(result.head.state=="ACTIVE")
//
//  }
//
//  test("Update Mail Setting "){
//    val update = Await.result(service.save(updatedEntity),2 minutes)
//    val result = Await.result(service.getAllMailSettings("CPUT"),2 minutes)
//    assert(result.head.state=="INACTIVE")
//
//  }

}
