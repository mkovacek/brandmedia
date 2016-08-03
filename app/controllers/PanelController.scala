package controllers
import javax.inject.{Inject, Singleton}
import play.api.cache.Cached
import play.api.mvc._

/**
  * Created by Matija on 3.8.2016..
  */
class PanelController @Inject() extends Controller{
  def panel = Action {
      Ok(views.html.userpanel.panel())
  }
}

