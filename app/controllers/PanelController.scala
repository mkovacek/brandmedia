package controllers

import javax.inject.Inject

import models.entities.User
import modules.Panel.PanelHandler
import modules.Security.Secured
import modules.Twitter.Twitter
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
class PanelController @Inject() (panel: PanelHandler, ws: WSClient) extends Controller with Secured {

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

  def tweets = Action { implicit  request =>

/*
     val twitterConsumerKey = "do213KmGfaxKfBDRpp0bL7ebp"
     val twitterConsumerSecret = "6SF6zxkSIewbKUaxGYQO4zGL1eCcN0TWaA2q8z77DJhDYnksvL"
     val accessTokenKey = "166725756-RTkRyo5nW7CSgMEMpgxYnHxI8R81R4ZsbtT2NQBd"
     val accessTokenSecret = "kmNgVXtJFbiMEtUgzS83PDfdnppwPi2LA8SBQoBA8UFmV"
     val url = "https://stream.twitter.com/1.1/statuses/filter.json"


    val consumerKey: ConsumerKey = ConsumerKey(twitterConsumerKey, twitterConsumerSecret)
    val requestToken: RequestToken = RequestToken(accessTokenKey, accessTokenSecret)


    val urlPath = "https://api.twitter.com/1.1/search/tweets.json?q=messi"
    ws.url(urlPath)
      .sign(OAuthCalculator(consumerKey, requestToken))
      .get
      .map(result => println(result.json))*/

    new Twitter(ws)

    Ok("Tweets")
  }

}

