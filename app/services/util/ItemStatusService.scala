package services.util

import repositories.util.ItemStatusRepository

/**
  * Created by hashcode on 2017/01/29.
  */
trait ItemStatusService extends ItemStatusRepository {
  
}


object ItemStatusService extends ItemStatusService with ItemStatusRepository
