package services.votes

import repositories.votes.VoteUpRepository


trait VoteUpService extends VoteUpRepository {

}


object VoteUpService extends VoteUpService with VoteUpRepository
