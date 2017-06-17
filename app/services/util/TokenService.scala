package services.util

import javax.inject.Singleton

import repositories.util.TokenRepository

/**
  * Created by hashcode on 2017/01/29.
  */
trait TokenService extends TokenRepository {}

@Singleton
object TokenService extends TokenService with TokenRepository
