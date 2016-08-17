package models.entities

/**
  * Created by Matija on 16.8.2016..
  */
case class User(email: String, password: String, active: Int, typeId: Long, details: Long,id: Long = 0L)
