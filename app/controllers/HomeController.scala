package controllers

import javax.inject.{Inject, Singleton}
import modules.Security.Secured
import forms.Forms
import play.api.cache.Cached
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() (cache: Cached, forms: Forms) extends Controller with Secured{

  def index(any: String) = cache("homePage"){
    //ako postoji session ili jwt redirect na /home
    Action {
      Ok(views.html.homepage.index())
    }
  }

  def signIn = cache("signInPage"){
    Action {
      Ok(views.html.homepage.authentication.signIn())
    }
  }
  def signUp = cache("signUpPage"){
    Action {
      Ok(views.html.homepage.authentication.signUp(forms.signUpForm))
    }
  }
}
