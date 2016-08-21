package controllers

import javax.inject.Inject

import models.entities.User
import modules.Panel.PanelHandler
import modules.Security.Secured
import modules.Twitter.{KillSwitch, Twitter}
import play.api.mvc._
import pdi.jwt._
import play.api.libs.json.Json
import play.api.libs.oauth.{ConsumerKey, OAuthCalculator, RequestToken}
import play.api.libs.ws._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.ExecutionContext
/**
  * Created by Matija on 3.8.2016..
  * Controller for rendering html parts of user panel page
  */
class PanelController @Inject() (panel: PanelHandler, ws: WSClient,killSwitch: KillSwitch) extends Controller with Secured {

  /*
  * Method for rendering panel layout view
  * */
  def panel(any: String) = Action {
    Ok(views.html.userpanel.panel(any))
  }

  /*
  * Method for rendering main content of panel view
  * */
  def content = Authenticated {implicit request =>
    val user = request.jwtSession.getAs[User]("user").get
    val userDetails = panel.getUserDetails(user.id)
    Ok(views.html.userpanel.content(userDetails))
  }

  /*
  * Method for rendering keywords view
  * */
  def keywords = Authenticated {
    Ok(views.html.userpanel.keywords())
  }

  /*
  * Method for rendering mentions view
  * */
  def mentions = Authenticated {
    Ok(views.html.userpanel.mentions())
  }

  /*
  * Method for rendering analytics view
  * */
  def analytics = Authenticated {
    Ok(views.html.userpanel.analytics())
  }

  /*
  * Method for rendering settings view
  * */

  def settings = Authenticated { implicit request =>
    val user = request.jwtSession.getAs[User]("user").get
    val userDetails = panel.getUserDetails(user.id)
    Ok(views.html.userpanel.settings(user,userDetails))
  }

  /*
  * Method for update user settings
  * */
  def updateSettings = Authenticated { implicit request =>
    val user = request.jwtSession.getAs[User]("user").get
    val data = request.body.asJson.get
    println(data)
    //data.name, data.surname, data.oldPassword, data.newPassword
    //provjeri stari pass ako je ok ažuriraj, vrati ok
    //ako nije ok vrati error..



    Ok(Json.obj("success" -> "successfully updated settings"))
    // zaprimi json, ažuriraj podatke
    // vrati ok response
    // angular napravi refresh stranice, da se fetchaju novi podaci

/*    forms.settingsForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.userpanel.settings(formWithErrors))
      },
      data => {
        val jwt = JwtSession() + ("user", auth.saveUser(data))
        Redirect(controllers.routes.PanelController.panel(jwt.serialize)).withJwtSession(jwt)
      }
    )

    val userDetails = panel.getUserDetails(user.id)
    val form = forms.settingsForm.fill(Settings(userDetails.name,userDetails.surname,user.email,"",""))
    Ok(views.html.userpanel.settings(form))*/
  }

  def tweets(query: String) = Action { implicit  request =>
    Twitter(query,ws,killSwitch).getTweets(query)
    //twitter.getTweets(query)
    Ok("Tweets")
  }

  def stop(name: String) = Action { implicit  request =>
    Twitter(name,ws,killSwitch).stopStream(name)
    //twitter.stopStream(name)
    Ok("Stop")
  }

}

