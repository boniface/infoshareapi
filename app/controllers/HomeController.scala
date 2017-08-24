package controllers

import javax.inject._

import play.api.mvc._
import services.setup.SetupService
import services.users.UserService

import scala.concurrent.ExecutionContext.Implicits.global


/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()
(cc: ControllerComponents) extends AbstractController(cc)  {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def setup = Action.async {
    SetupService.init map( results =>Ok("Done") )
  }

  def cleanup = Action.async {
    SetupService.cleanup map( results =>Ok("Done" ) )
  }

  def init = Action.async {
    SetupService.init map( results =>Ok("Done") )
  }

  def save = Action {
    Ok("Created ")
  }



}
