package controllers

import javax.inject.{Inject, Singleton}
import forms.Forms
import play.api.cache.Cached
import play.api.mvc._

/**
  * Created by Matija on 2.8.2016..
  * Controller for rendering html parts of index page
  */
@Singleton
class HomeController @Inject() (cache: Cached, forms: Forms) extends Controller {

  /*
  * Method for rendering index html page
  * */
  def index(any: String) = cache("homePage"){
    Action {
      Ok(views.html.homepage.index())
    }
  }

  /*
* Method for rendering index html page for loged user
* */
  def indexLogIn() = cache("homePageLogIn"){
    Action {
      Ok(views.html.homepage.indexLogIn())
    }
  }

  /*
  * Method for rendering sign in html page
  * */
  def signIn = cache("signInPage"){
    Action {
      Ok(views.html.homepage.authentication.signIn(forms.signInForm))
    }
  }

  /*
  * Method for rendering sign up html page
  * */
  def signUp = cache("signUpPage"){
    Action {
      Ok(views.html.homepage.authentication.signUp(forms.signUpForm))
    }
  }
}
