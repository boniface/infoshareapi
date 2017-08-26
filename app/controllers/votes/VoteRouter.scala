package controllers.votes

import javax.inject.Inject

import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class VoteRouter @Inject()(voteCtrl: VoteCtrl) extends SimpleRouter {
  override def routes: Routes = {

    case POST(p"/castvote") =>
      voteCtrl.castVote

    case GET(p"/all/$siteId/$itemId/") =>
      voteCtrl.getAllVotes(siteId, itemId)

    case GET(p"/vote/$siteId/$itemId/$ipAddress") =>
      voteCtrl.getVoteById(siteId, itemId, ipAddress)

    case GET(p"/status/$siteId/$itemId/$status") =>
      voteCtrl.getVotesByState(siteId, itemId, status)

    case GET(p"/user/all/$siteId/$itemOwnerId") =>
      voteCtrl.getAllUserVotes(siteId, itemOwnerId)

    case GET(p"user/status/$siteId/$itemId/$status") =>
      voteCtrl.getUserVotesByState(siteId, itemId, status)
  }

}
