package services.comments

import repositories.comments.AbuseRepository


trait AbuseService extends AbuseRepository {

}

object AbuseService extends AbuseService with AbuseRepository
