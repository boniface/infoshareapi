package services.votes

import repositories.votes.VoteDownRepository

/**
  * Created by hashcode
  */
trait VoteDownService extends VoteDownRepository {

}

object VoteDownService extends VoteDownService with VoteDownRepository
