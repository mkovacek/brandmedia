package controllers

import javax.inject.{Inject, Singleton}

import forms.Forms
import modules.Authentication.AuthHandler
import play.api.cache.Cached
import play.api.mvc._
import pdi.jwt._

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
        /*Redirect("/home").addingToJwtSession("user",auth.saveUser(data))*/
        val user = auth.saveUser(data);
        val jwt = JwtSession() + ("user", user)
        /*Ok(views.html.userpanel.panel(jwt.serialize)).withJwtSession(jwt)*/
        //Redirect("/home").withJwtSession(jwt)
        Redirect(controllers.routes.PanelController.panel(jwt.serialize)).withJwtSession(jwt)
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
