package services.setup

import com.outworkers.phantom.dsl.ResultSet
import domain.users.{User, UserRole}
import domain.util.{RoleValues, Roles}
import org.joda.time.DateTime
import repositories.comments._
import repositories.errors.ErrorReportDatabase
import repositories.feeds.FeedsDatabase
import repositories.links.LinksDatabase
import repositories.posts.RawPostsDatabase
import repositories.stats.StatsDatabase
import repositories.users.{ReputationDatabase, UserDatabase, UserRoleDatabase, UserStatusDatabase}
import repositories.util._
import repositories.votes.{VoteDownDatabase, VoteUpDatabase}
import repositories.zones.{AdministratorsDatabase, SiteDatabase, ZoneDatabase}
import services.users.UserCreationService
import services.util.RoleService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by hashcode on 2017/01/29.
  */
object SetupService {
  def setup: Future[ResultSet] = {
    implicit val session = ZoneDatabase.session
    implicit val keyspace = ZoneDatabase.space

    for {
    // Zones Package
      setup <- ZoneDatabase.zoneTable.create.ifNotExists().future()
      setup <- SiteDatabase.siteTable.create.ifNotExists().future()
      setup <- AdministratorsDatabase.administratorTable.create.ifNotExists().future()

      // Votes
      setup <- VoteDownDatabase.votesDownTable.create.ifNotExists().future()
      setup <- VoteDownDatabase.userDownVotesTable.create.ifNotExists().future()
      setup <- VoteUpDatabase.voteUpTable.create.ifNotExists().future()
      setup <- VoteUpDatabase.userUpVotesTable.create.ifNotExists().future()

      // Util
      setup <- ItemStatusDatabase.itemStatusTable.create.ifNotExists().future()
      setup <- KeysDatabase.keysTable.create.ifNotExists().future()
      setup <- MailDatabase.mailTable.create.ifNotExists().future()
      setup <- RoleDatabase.roleTable.create.ifNotExists().future()
      setup <- TokenDatabase.tokenTable.create.ifNotExists().future()

      // Users

      setup <- ReputationDatabase.reputationTable.create.ifNotExists().future()
      setup <- UserRoleDatabase.userRoleTable.create.ifNotExists().future()
      setup <- UserStatusDatabase.userStatusTable.create.ifNotExists().future()
      setup <- UserDatabase.userTable.create.ifNotExists().future()
      setup <- UserDatabase.siteUserTable.create.ifNotExists().future()


     
      // Error Report
      setup <- ErrorReportDatabase.errorReportTable.create.ifNotExists().future()



    } yield setup
  }

  def init: Future[Boolean] = {

    val roles = Seq(
      Roles(RoleValues.ANONYMOUS, RoleValues.ANONYMOUS),
      Roles(RoleValues.ADMINISTRATOR, RoleValues.ADMINISTRATOR),
      Roles(RoleValues.MODERATOR, RoleValues.MODERATOR),
      Roles(RoleValues.READER, RoleValues.READER),
      Roles(RoleValues.SITEADMIN, RoleValues.SITEADMIN)
    )
    roles.foreach(role => RoleService.save(role))

    val admin = User("HASHCODE", "admin@africahash.com", "ADMIN", None, None, "passwd", "ACTIVE", new DateTime())
    val anon = User("ZM", "anon@africahash.com", "ADMIN", None, None, "passwd", "ACTIVE", new DateTime())
    val moderator = User("ZM", "moderator@africahash.com", "ADMIN", None, None, "passwd", "ACTIVE", new DateTime())
    val reader = User("ZM", "reader@africahash.com", "ADMIN", None, None, "passwd", "ACTIVE", new DateTime())
    val siteadmin = User("ZM", "siteadmin@africahash.com", "ADMIN", None, None, "passwd", "ACTIVE", new DateTime())

    for {
      createUser <- UserCreationService.apply.createUser(admin, UserRole(admin.email, new DateTime(), RoleValues.ADMINISTRATOR))
      createUser <- UserCreationService.apply.createUser(anon, UserRole(anon.email, new DateTime(), RoleValues.ANONYMOUS))
      createUser <- UserCreationService.apply.createUser(moderator, UserRole(moderator.email, new DateTime(), RoleValues.MODERATOR))
      createUser <- UserCreationService.apply.createUser(reader, UserRole(reader.email, new DateTime(), RoleValues.READER))
      createUser <- UserCreationService.apply.createUser(siteadmin, UserRole(siteadmin.email, new DateTime(), RoleValues.SITEADMIN))
    } yield createUser
  }
}
