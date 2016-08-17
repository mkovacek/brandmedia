package forms

import javax.inject.{Inject, Singleton}

import modules.Authentication.AuthHandler
import play.api.data.Form
import play.api.data.Forms._

/**
  * Created by Matija on 17.8.2016..
  */
@Singleton
class Forms @Inject() (auth: AuthHandler) {
  val signUpForm: Form[UserForm] = Form(
    mapping(
      "name" -> nonEmptyText,
      "surname" -> nonEmptyText,
      "email" -> email,
      "password" -> nonEmptyText,
      "repeatPassword" -> nonEmptyText
    )(UserForm.apply)(UserForm.unapply)
      .verifying ("passwordNotMatch", data => data.password == data.repeatPassword)
      .verifying ("emailExists", data => auth.isEmailUnique(data.email))
  )
}

case class UserForm (name: String, surname: String, email: String, password: String, repeatPassword: String)