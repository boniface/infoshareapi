package services.security.Impl

import org.apache.commons.lang3.RandomStringUtils
import org.mindrot.jbcrypt.BCrypt
import services.security.AuthenticationService

/**
  * Created by hashcode on 2017/06/07.
  */
class AuthenticationServiceImpl extends AuthenticationService{
  override def getHashedPassword(password: String): String = {
    BCrypt.hashpw(password, BCrypt.gensalt(12))
  }

  override def generateRandomPassword(length: Int = 8,useLetters: Boolean = true, useNumbers: Boolean = true): String = {
    RandomStringUtils.random(length, useLetters, useNumbers)
  }

  override def checkPassword(candidate: String, hashed: String): Boolean = {
    BCrypt.checkpw(candidate, hashed)
  }
}


