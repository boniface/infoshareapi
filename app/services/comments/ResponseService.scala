package services.comments

import repositories.comments.ResponseRepository

/**
  * Created by hashcode
  */
trait ResponseService extends ResponseRepository {

}


object ResponseService extends ResponseService with ResponseRepository
