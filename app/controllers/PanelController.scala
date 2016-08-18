package controllers

import modules.Security.Secured
import play.api.mvc._

/**
  * Created by Matija on 3.8.2016..
  */
class PanelController extends Controller with Secured{

  def panel(any: String) = Action {
    Ok(views.html.userpanel.panel(any))
  }

  def content = Authenticated{
    Ok(views.html.userpanel.content())
  }

  def keywords = Authenticated{
    Ok(views.html.userpanel.keywords())
  }

  def mentions = Authenticated{
    Ok(views.html.userpanel.mentions())
  }

  def analytics = Authenticated{
    Ok(views.html.userpanel.analytics())
  }

  def settings = Authenticated{
    Ok(views.html.userpanel.settings())
  }

}

