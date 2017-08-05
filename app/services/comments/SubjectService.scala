package services.comments

import repositories.comments.SubjectRepository

/**
  * Created by hashcode
  */
trait SubjectService extends SubjectRepository {

}

object SubjectService extends SubjectService with SubjectRepository
