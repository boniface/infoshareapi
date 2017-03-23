package conf.util

import java.util.{Date, Random}

import com.gravity.goose.{Configuration, Goose}
import org.apache.commons.lang3.RandomStringUtils
import org.joda.time.{DateTime, DateTimeConstants}

import scala.util.{Failure, Success, Try}

/**
  * Created by hashcode on 2015/11/07.
  */
object Util {

  import java.net._

  def md5Hash(text: String): String = {
    val hash = text + InetAddress.getLocalHost.getHostName
    java.security.MessageDigest.getInstance("MD5").digest(hash.getBytes()).map(0xFF & _).map {
      "%02x".format(_)
    }.foldLeft("") {
      _ + _
    }
  }


  private def generateOrganisationCode(name: String): String = {
    val count: Int = 4
    val useLetters: Boolean = true
    val useNumbers: Boolean = false
    val choseFrom = name.toCharArray
    RandomStringUtils.random(count, 0, 0, useLetters, useNumbers, choseFrom, new Random())
  }

  private def getSalt(): String = {
    val length: Int = 5
    val useLetters: Boolean = true
    val useNumbers: Boolean = true
    RandomStringUtils.random(length, useLetters, useNumbers)
  }

  def codeGen(name: String): String = {
    val code = generateOrganisationCode(name)
      .toCharArray
      .sortWith(_ < _)
      .mkString("").toUpperCase

    val salt = getSalt()
      .toCharArray
      .sortWith(_ < _)
      .mkString("").toUpperCase
    (code + "-" + salt)
  }

  def getBrowser ={
    val config = new Configuration
    config.setBrowserUserAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.99 Safari/537.36")
    config.setBrowserReferer("https://www.google.com/")

    new Goose(config)
  }
  def getDate(date: String): Date = {
    date match{
      case "TODAY" =>  DateTime.now.withTimeAtStartOfDay().toLocalDate.toDate
      case "YESTERDAY"=>  DateTime.now.minusDays(1).toDate
      case "WEEK" =>  DateTime.now.withTimeAtStartOfDay.withDayOfWeek(DateTimeConstants.SUNDAY).minusDays(7).toDate
      case "MONTH" =>  DateTime.now.withTimeAtStartOfDay.dayOfMonth.withMinimumValue.toDate
      case _ => DateTime.now.toDate
    }
  }

  def getDateFromString(date:String):Date ={
    DateTime.parse(date).toDate
  }

  def yearDate = {
    DateTime.now.dayOfYear().withMinimumValue().withTimeAtStartOfDay().toDate
  }
  def getIntFromString(value:String):Int ={
    Try(Integer.parseInt(value)) match {
      case Success(ans) => ans
      case Failure(ex) => 0
    }
  }

}
