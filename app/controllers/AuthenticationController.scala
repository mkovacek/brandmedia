package controllers

import javax.inject.{Inject, Singleton}
import play.api.cache.Cached
import play.api.mvc._


/**
  * Created by Matija on 2.8.2016..
  */
@Singleton
class AuthenticationController @Inject() (cache: Cached) extends Controller{
  def signIn = cache("signInPage"){
    Action {
      Ok(views.html.authentication.signIn())
    }
  }
  def signUp = cache("signUpPage"){
    Action {
      Ok(views.html.authentication.signUp())
    }
  }
  def register = ???
  def login = ???

  def logout =  Action {
    //remove user session
    Redirect("/")
  }
}
