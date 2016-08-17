package controllers

import javax.inject.{Inject, Singleton}

import forms.Forms
import modules.Authentication.AuthHandler
import play.api.cache.Cached
import play.api.mvc._


/**
  * Created by Matija on 2.8.2016..
  */
@Singleton
class AuthController @Inject()(cache: Cached, auth: AuthHandler, forms: Forms) extends Controller{
  def signUp = Action {implicit request =>
    forms.signUpForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.homepage.authentication.signUp(formWithErrors))
      },
      data => {
        println("ok")
        auth.saveUser(data)
        Redirect("/signup")
      }
    )
  }
  def signIn = Action {
    Redirect("/home")
  }
  def logout =  Action {
    //remove user session
    Redirect("/")
  }
}
