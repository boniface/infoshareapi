package services.votes

import repositories.votes.VoteDownRepository

/**
  * Created by hashcode on 2017/01/29.
  */
trait VoteDownService extends VoteDownRepository {

}

object VoteDownService extends VoteDownService with VoteDownRepository
