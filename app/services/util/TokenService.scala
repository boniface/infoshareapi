package services.util

import repositories.util.TokenRepository

/**
  * Created by hashcode on 2017/01/29.
  */
trait TokenService extends TokenRepository {



}

object TokenService extends TokenService with TokenRepository
