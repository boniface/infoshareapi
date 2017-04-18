package repositories.storage.tables


//todo : work on fileMeta repo

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.storage.FileMeta

class FileMetaTable extends CassandraTable[FileMetaTable, FileMeta] {

  object name extends StringColumn(this) with PartitionKey

  object contentType extends StringColumn(this)

}
//
//abstract class FileMetaTableImpl extends FileMetaTable with RootConnector {
//  override lazy val tableName = "fileMeta"
//
//  def getFileType(name: String): String = {
//    URLConnection.guessContentTypeFromName(name)
//  }
//
//  def save(meta:FileMeta):ObjectId = {
//    val fileId = gridfs(file) { f =>
//      f.filename = meta.fileName
//      f.contentType = meta.contentType
//    }
//
//    fileId.get.asInstanceOf[ObjectId]
//  }
//
//  def getFileById(id: String): Option[GridFSDBFile] = {
//    gridfs.findOne(new ObjectId(id))
//  }
//
//  def getFilesByName(fileName: String): Option[GridFSDBFile] = {
//    gridfs.findOne(fileName)
//
//  }
//}