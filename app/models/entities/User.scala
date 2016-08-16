package models.entities

/**
  * Created by Matija on 16.8.2016..
  */
case class User(id: Long, email: String, password: String, active: Int, typeId: Long, detailsId: Long)
