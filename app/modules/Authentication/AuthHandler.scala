package modules.Authentication

import javax.inject.Inject
import forms.{SignIn, SignUp}
import models.daos.UserDAO
import models.entities.{User, UserDetails}
import utils.Bcrypt
import scala.concurrent.Await
import scala.concurrent.duration._


/**
  * Created by Matija on 17.8.2016..
  * Authentication handler
  */
case class AuthHandler @Inject()(userDAO: UserDAO){

  val ACTIVE = 1
  val USER = 2

  /*
  * Method for check if email is unique
  * */
  def isEmailUnique(email: String): Boolean = {
    Await.result(userDAO.findByEmail(email), 1 second) match {
      case Some(user) => false
      case None => true
    }
  }

  /*
  * Method for save new user
  * */
  def saveUser(data: SignUp): User = {
    val userDetails = UserDetails(data.name, data.surname)
    val userDetailsId = Await.result(userDAO.insertUserDetails(userDetails),1 second).id
    val user = User(data.email,Bcrypt.encrypt(data.password),ACTIVE,USER,userDetailsId)
    Await.result(userDAO.insertUser(user),1 second)
  }

  /*
  * Method for check login credentials
  * */
  def checkCredentials(data: SignIn): Boolean = {
    Await.result(userDAO.findByEmail(data.email), 1 second) match {
      case Some(user) => Bcrypt.validate(data.password,user.password)
      case None => false
    }
  }

  /*
  * Method for authentication
  * */
  def authenticate(data: SignIn): User = {
    Await.result(userDAO.findByEmail(data.email), 1 second).get
  }

}
