package controllers
import javax.inject.{Inject, Singleton}
import play.api.cache.Cached
import play.api.mvc._

/**
  * Created by Matija on 3.8.2016..
  */
class PanelController @Inject() extends Controller{
  def panel(any: String) = Action {
      Ok(views.html.userpanel.panel())
  }

  def keywords = Action{
    Ok(views.html.userpanel.keywords())
  }

  def mentions = Action{
    Ok(views.html.userpanel.mentions())
  }

  def analytics = Action{
    Ok(views.html.userpanel.analytics())
  }

  def settings = Action{
    Ok(views.html.userpanel.settings())
  }
}

