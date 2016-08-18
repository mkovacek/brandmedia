package models.entities

import play.api.libs.json.Json

/**
  * Created by Matija on 16.8.2016..
  */
case class User(email: String, password: String, active: Int, typeId: Long, detailsId: Long, id: Long = 0L)

object User {
  implicit val userFormat = Json.format[User]
}
