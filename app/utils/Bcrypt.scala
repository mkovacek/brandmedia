package utils

import com.github.t3hnar.bcrypt._
/**
  * Created by Matija on 17.8.2016..
  */
object Bcrypt {
  def encrypt(password: String): String = password.bcrypt
  def validate(password: String, hash: String): Boolean = password.isBcrypted(hash)
}
