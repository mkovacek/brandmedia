package modules.Authentication

import javax.inject.Inject
import forms.UserForm
import models.daos.UserDAO
import models.entities.{User, UserDetails}
import utils.Bcrypt
import scala.concurrent.Await
import scala.concurrent.duration._


/**
  * Created by Matija on 17.8.2016..
  */
case class AuthHandler @Inject()(userDAO: UserDAO){
  val ACTIVE = 1
  val USER = 2

  def isEmailUnique(email: String): Boolean = {
    Await.result(userDAO.findByEmail(email), 1 second) match {
      case Some(user) => false
      case None => true
    }
  }
  def saveUser(data: UserForm): User = {
    //generiraj hash i paralelno s userDetails to pokreni
    val userDetails = UserDetails(data.name, data.surname)
    val userDetailsId = Await.result(userDAO.insertUserDetails(userDetails),1 second).id
    val user = User(data.email,Bcrypt.encrypt(data.password),ACTIVE,USER,userDetailsId)
    val savedUser = Await.result(userDAO.insertUser(user),1 second) //stavi izvršenje u ekstra varijablu u međuvremenu napravi jwt i onda stavi u blok, zaprvo niti netreba blokirati
    //jwt
    println(savedUser)
    savedUser
    /*   val user = User(data.email,data.password,1,2,userDetails.id)*/
  }


}
