package controllers

import javax.inject.Inject

import models.entities.User
import modules.Panel.PanelHandler
import modules.Security.Secured
import play.api.mvc._
import pdi.jwt._
import play.api.cache.Cached


/**
  * Created by Matija on 3.8.2016..
  * Controller for rendering html parts of user panel page
  */
class PanelController @Inject() (cache: Cached, panel: PanelHandler) extends Controller with Secured {

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
    Ok(views.html.userpanel.content(panel.getUserDetails(user.id)))
  }

  /*
  * Method for rendering keywords view
  * */
  def keywords = Authenticated {  //cache
    Ok(views.html.userpanel.keywords())
  }

  /*
  * Method for rendering mentions view
  * */
  def mentions = Authenticated { //cache
    Ok(views.html.userpanel.mentions())
  }

  /*
  * Method for rendering analytics view
  * */
  def analytics = Authenticated { //cache
    Ok(views.html.userpanel.analytics())
  }

  
  /*
  * Method save new keyword and returns  all user keywords as Json
  * */
  def addKeyword() = Authenticated(BodyParsers.parse.json) { request =>
    Ok(panel.saveKeyword(request))
  }

  /*
  * Method returns all user keywords as Json
  * */
  def keywordsList() = Authenticated { implicit  request =>
    Ok(panel.getAllKeywords(request))
  }

  /*
  * Method returns all user active keywords as Json
  * */
  def activeKeywordsList() = Authenticated { implicit  request =>
    Ok(panel.getActiveKeywords(request))
  }

  /*
  * Method update keyword update status to active and start twitter stream
  * */
  def updateAndStartSearch() = Authenticated(BodyParsers.parse.json) { request =>
    Ok(panel.updateAndStartStream(request))
  }

  /*
  * Method update keyword update status to inactive and stop twitter stream
  * */
  def updateAndStopSearch() = Authenticated(BodyParsers.parse.json) { request =>
    Ok(panel.updateAndStopStream(request))
  }



}

