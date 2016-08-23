package controllers

import javax.inject.{Inject, Singleton}

import forms.Forms
import modules.Authentication.AuthHandler
import modules.Security.Secured
import play.api.mvc._
import pdi.jwt._
import play.api.libs.json.Json

/**
  * Created by Matija on 2.8.2016..
  * Controller for Authentication actions
  */
@Singleton
class AuthController @Inject()(auth: AuthHandler, forms: Forms) extends Controller with Secured{

  /*
  * Method for user registration
  * */
  def signUp = Action {implicit request =>
    forms.signUpForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.homepage.authentication.signUp(formWithErrors))
      },
      data => {
        val jwt = JwtSession() + ("user", auth.saveUser(data))
        Redirect(controllers.routes.PanelController.panel(jwt.serialize)).withJwtSession(jwt)
      }
    )
  }

  /*
  * Method for user login
  * */
  def signIn =  Action {implicit request =>
    forms.signInForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.homepage.authentication.signIn(formWithErrors))
      },
      data => {
        val jwt = JwtSession() + ("user", auth.authenticate(data))
        Redirect(controllers.routes.PanelController.panel(jwt.serialize)).withJwtSession(jwt)
      }
    )
  }

  /*
   * Method for user logout
   * */
  def logout =  Authenticated {
    Ok(Json.obj("success" -> "successfully logout")).withoutJwtSession
  }
}
