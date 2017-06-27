package services.content

import javax.inject.Singleton

import com.outworkers.phantom.dsl.ResultSet
import domain.content.Category
import repositories.content.CategoryRepository

import scala.concurrent.Future

trait CategoryService extends CategoryRepository {

  def save(category: Category): Future[ResultSet] = {
    database.categoryTable.save(category)
  }

  def getById(id: String): Future[Option[Category]] = {
    database.categoryTable.getById(id)
  }

  def getAll: Future[Seq[Category]] = {
    database.categoryTable.getAll
  }

}
@Singleton
object CategoryService extends CategoryService with CategoryRepository
