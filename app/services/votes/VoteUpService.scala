package services.votes

import repositories.votes.VoteUpRepository

/**
  * Created by hashcode on 2017/01/29.
  */
trait VoteUpService extends VoteUpRepository {

}


object VoteUpService extends VoteUpService with VoteUpRepository
