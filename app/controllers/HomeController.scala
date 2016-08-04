package controllers

import javax.inject.{Inject, Singleton}
import play.api.cache.Cached
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() (cache: Cached) extends Controller {

/*  def main(any: String) = Action {
    Ok(views.html.main("Brandmedia"))
  }*/

  def index(any: String) = cache("homePage"){
    //ako postoji session ili jwt redirect na /home
    Action {
      Ok(views.html.homepage.index("homepage"))
    }
  }


}
