package services.content

import com.outworkers.phantom.dsl.ResultSet
import domain.content.Category
import repositories.content.CategoryRepository

import scala.concurrent.Future


trait CategoryService extends CategoryRepository{

  def save(category: Category): Future[ResultSet] = {
    database.categoryTable.save(category)
  }

  def getCategoryById(id: String): Future[Option[Category]] = {
    database.categoryTable.getCategoryById(id)
  }

  def getAllCategories: Future[Seq[Category]] = {
    database.categoryTable.getAllCategories
  }

}

object CategoryService extends CategoryService with CategoryRepository