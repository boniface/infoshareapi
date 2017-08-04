package services.setup

import java.time.LocalDateTime

import com.outworkers.phantom.dsl.{ResultSet, context}
import conf.util.{HashcodeKeys, RolesID}
import domain.security.Roles
import domain.users.{User, UserRole}
import domain.util.Keys
import repositories.content._
import repositories.demographics._
import repositories.location._
import repositories.organisation._
import repositories.storage._
import repositories.syslog._
import repositories.users._
import repositories.util._
import services.security.{AuthenticationService, TokenGenerationService}
import services.users.{UserRoleService, UserService}
import services.util.RolesService

import scala.concurrent.Future

/**
  * Created by hashcode on 2017/04/21.
  */
object SetupService {

  def init: Future[Boolean] = {

    val publicKey = TokenGenerationService.apply.GenerateKey(HashcodeKeys.PUBLICKEY)
    val key = TokenGenerationService.apply.getJsonKey(publicKey)
    val keys = Keys(HashcodeKeys.PUBLICKEY, key, HashcodeKeys.ACTIVE)

//    KeysService.save(keys)

    val mailKey = Keys(HashcodeKeys.MAILKEY, "", HashcodeKeys.ACTIVE)

//        KeysService.save(mailKey)

    val roles = Seq(
      Roles(RolesID.ADMIN, RolesID.ADMIN),
      Roles(RolesID.CONTENT_CREATOR, RolesID.CONTENT_CREATOR),
      Roles(RolesID.MODERATOR, RolesID.MODERATOR),
      Roles(RolesID.READER, RolesID.READER),
      Roles(RolesID.EDITOR, RolesID.EDITOR),
      Roles(RolesID.SITE_ADMIN, RolesID.SITE_ADMIN),
      Roles(RolesID.PUBLISHER, RolesID.PUBLISHER)
    )
    roles.foreach(role => RolesService.save(role))
    val passwd = AuthenticationService.apply.getHashedPassword("passwd")
    val admin = User("CPUT","test@test.com",None, None,"test" ,password= passwd, HashcodeKeys.ACTIVE, LocalDateTime.now)
    val userRole = UserRole(admin.siteId,admin.email,LocalDateTime.now(),RolesID.ADMIN)
    for {
      save <- UserService.saveOrUpdate(admin)
      save <- UserRoleService.save(userRole)
    } yield save.isExhausted

  }

  def setup: Future[ResultSet] = {
    implicit val session = UserDatabase.session
    implicit val keyspace = UserDatabase.space

    for {
    //    util
      setup <- ItemStatusDatabase.itemStatusTable.create.ifNotExists().future()
      setup <- KeysDatabase.keysTable.create.ifNotExists().future()
      setup <- MailDatabase.mailTable.create.ifNotExists().future()
      setup <- TokenDatabase.tokenTable.create.ifNotExists().future()
      setup <- RolesDatabase.rolesTable.create.ifNotExists().future()

      //     user
      setup <- UserAddressDatabase.userAddressTable.create.ifNotExists().future()
      setup <- UserContactDatabase.userContactTable.create.ifNotExists().future()
      setup <- UserDemographicsDatabase.userDemographicsTable.create.ifNotExists().future()
      setup <- UserLanguageDatabase.userLanguageTable.create.ifNotExists().future()
      setup <- UserImagesDatabase.userImagesTable.create.ifNotExists().future()
      setup <- UserDatabase.userTable.create.ifNotExists().future()
      setup <- UserDatabase.userTimeLineTable.create.ifNotExists().future()
      setup <- UserDatabase.siteUserTable.create.ifNotExists().future()
      setup <- UserRoleDatabase.userRoleTable.create.ifNotExists().future()

      //      content
      setup <- CategoryDatabase.categoryTable.create.ifNotExists().future()
      setup <- ContentTypeDatabase.contentTypeTable.create.ifNotExists().future()
      setup <- EditedContentDatabase.editedContentTable.create.ifNotExists().future()
      setup <- MediaDatabase.mediaTable.create.ifNotExists().future()
      setup <- PublishedContentDatabase.publishedContentTable.create.ifNotExists().future()
      setup <- RawContentDatabase.rawContentTable.create.ifNotExists().future()
      setup <- SourceDatabase.sourceTable.create.ifNotExists().future()

      //    demographics
      setup <- GenderDatabase.genderTable.create.ifNotExists().future()
      setup <- LanguageDatabase.languageTable.create.ifNotExists().future()
      setup <- LanguageProficiencyDatabase.languageProficiencyTable.create.ifNotExists().future()
      setup <- RaceDatabase.raceTable.create.ifNotExists().future()
      setup <- MaritalStatusDatabase.maritalStatusTable.create.ifNotExists().future()
      setup <- RolesDatabase.rolesTable.create.ifNotExists().future()

      //    location
      setup <- AddressTypeDatabase.addressTypeTable.create.ifNotExists().future()
      setup <- ContactTypeDatabase.contactTypeTable.create.ifNotExists().future()
      setup <- LocationTypeDatabase.locationTypeTable.create.ifNotExists().future()

      //    storage
      setup <- StorageUrlDatabase.storageUrlTable.create.ifNotExists().future()
      setup <- FileResultsDatabase.fileResultsTable.create.ifNotExists().future()

      // Valid User
      setup <- ValidUserDatabase.validUserTable.create.ifNotExists().future()
      setup <- ValidUserDatabase.timeLineValidUserTable.create.ifNotExists().future()
      // system log event
      setup <- SystemLogEventsDatabase.systemLogEventsTable.create.ifNotExists().future()

      // Organization
      setup <- LocationDatabase.locationTable.create.ifNotExists().future()
      setup <- OrganisationDatabase.organisationTable.create.ifNotExists().future()
      setup <- OrganisationLogoDatabase.organisationLogoTable.create.ifNotExists().future()

    } yield setup
  }

  def cleanup: Future[Seq[ResultSet]] = {
    implicit val session = UserDatabase.session
    implicit val keyspace = UserDatabase.space

    for {
      truncate <- ItemStatusDatabase.dropAsync()
      truncate <- KeysDatabase.dropAsync()
      truncate <- MailDatabase.dropAsync()
      truncate <- TokenDatabase.dropAsync()
      truncate <- RolesDatabase.dropAsync()

      //     user
      truncate <- UserAddressDatabase.dropAsync()
      truncate <- UserContactDatabase.dropAsync()
      truncate <- UserDemographicsDatabase.dropAsync()
      truncate <- UserLanguageDatabase.dropAsync()
      truncate <- UserImagesDatabase.dropAsync()
      truncate <- UserRoleDatabase.dropAsync()
      truncate <- UserDatabase.dropAsync()

      //      content
      truncate <- CategoryDatabase.dropAsync()
      truncate <- ContentTypeDatabase.dropAsync()
      truncate <- EditedContentDatabase.dropAsync()
      truncate <- MediaDatabase.dropAsync()
      truncate <- PublishedContentDatabase.dropAsync()
      truncate <- RawContentDatabase.dropAsync()
      truncate <- SourceDatabase.dropAsync()

      //    demographics
      truncate <- GenderDatabase.dropAsync()
      truncate <- LanguageDatabase.dropAsync()
      truncate <- LanguageProficiencyDatabase.dropAsync()
      truncate <- RaceDatabase.dropAsync()
      truncate <- MaritalStatusDatabase.dropAsync()
      truncate <- RolesDatabase.dropAsync()

      //    location
      truncate <- AddressTypeDatabase.dropAsync()
      truncate <- ContactTypeDatabase.dropAsync()
      truncate <- LocationTypeDatabase.dropAsync()

      //    storage
      truncate <- StorageUrlDatabase.dropAsync()
      truncate <- FileResultsDatabase.dropAsync()

      // Valid User
      truncate <- ValidUserDatabase.dropAsync()
      // system log event
      truncate <- SystemLogEventsDatabase.dropAsync()

      // Organization
      truncate <- LocationDatabase.dropAsync()
      truncate <- OrganisationDatabase.dropAsync()
      truncate <- OrganisationLogoDatabase.dropAsync()

    } yield truncate
  }

}
