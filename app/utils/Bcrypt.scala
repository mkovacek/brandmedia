package utils

import com.github.t3hnar.bcrypt._
/**
  * Created by Matija on 17.8.2016..
  * Bcrypt hashing
  */
object Bcrypt {

  /*
  * Method encrypt password
  * */
  def encrypt(password: String): String = password.bcrypt

  /*
  * Method validate plain password with encrypted password
  * */
  def validate(password: String, hash: String): Boolean = password.isBcrypted(hash)
}
