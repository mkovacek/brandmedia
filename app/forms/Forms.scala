package forms

import javax.inject.{Inject, Singleton}

import modules.Authentication.AuthHandler
import play.api.data.Form
import play.api.data.Forms._

/**
  * Created by Matija on 17.8.2016..
  * Form validators
  */
@Singleton
class Forms @Inject() (auth: AuthHandler) {

  /*
  * Sign up form validator
  * */
  val signUpForm: Form[SignUp] = Form(
    mapping(
      "name" -> nonEmptyText,
      "surname" -> nonEmptyText,
      "email" -> email,
      "password" -> nonEmptyText,
      "repeatPassword" -> nonEmptyText
    )(SignUp.apply)(SignUp.unapply)
      .verifying ("passwordNotMatch", data => data.password == data.repeatPassword)
      .verifying ("emailExists", data => auth.isEmailUnique(data.email))
  )

  /*
  * Sign in form validator
  * */
  val signInForm: Form[SignIn] = Form(
    mapping(
      "email" -> email,
      "password" -> nonEmptyText
    )(SignIn.apply)(SignIn.unapply)
      .verifying ("wrongCredentials", data => auth.checkCredentials(data))
  )

  /*
  * Settings form validator
  * */
/*  val settingsForm: Form[Settings] = Form(
    mapping(
      "name" -> nonEmptyText,
      "surname" -> nonEmptyText,
      "email" -> nonEmptyText,
      "oldPassword" -> nonEmptyText,
      "newPassword" -> nonEmptyText
    )(Settings.apply)(Settings.unapply)
      .verifying ("wrongOldPassword", data => auth.checkPassword(data))
  )*/
}

/**
  * Form's data
  */
case class SignUp(name: String, surname: String, email: String, password: String, repeatPassword: String)
case class SignIn(email: String, password: String)
/*
case class Settings(name: String, surname: String, email: String, oldPassword: String, newPassword: String)*/
