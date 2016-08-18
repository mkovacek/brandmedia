package models.entities

import play.api.libs.json.Json

/**
  * Created by Matija on 16.8.2016..
  * User entity
  */
case class User(email: String, password: String, active: Int, typeId: Long, detailsId: Long, id: Long = 0L)

/**
  * For direct accessing User data (for JWT token)
  */
object User {
  implicit val userFormat = Json.format[User]
}
