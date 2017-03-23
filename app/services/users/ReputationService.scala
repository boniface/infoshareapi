package services.users

import repositories.users.ReputationRepository

/**
  * Created by hashcode on 2017/01/29.
  */
trait ReputationService extends ReputationRepository {

}

object ReputationService extends ReputationService with ReputationRepository
