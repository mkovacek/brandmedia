package controllers

import modules.Security.Secured
import play.api.mvc._

/**
  * Created by Matija on 3.8.2016..
  * Controller for rendering html parts of user panel page
  */
class PanelController extends Controller with Secured{

  /*
  * Method for rendering panel layout view
  * */
  def panel(any: String) = Action {
    Ok(views.html.userpanel.panel(any))
  }

  /*
  * Method for rendering main content of panel view
  * */
  def content = Authenticated{
    Ok(views.html.userpanel.content())
  }

  /*
  * Method for rendering keywords view
  * */
  def keywords = Authenticated{
    Ok(views.html.userpanel.keywords())
  }

  /*
  * Method for rendering mentions view
  * */
  def mentions = Authenticated{
    Ok(views.html.userpanel.mentions())
  }

  /*
  * Method for rendering analytics view
  * */
  def analytics = Authenticated{
    Ok(views.html.userpanel.analytics())
  }

  /*
  * Method for rendering settings view
  * */
  def settings = Authenticated{
    Ok(views.html.userpanel.settings())
  }

}

