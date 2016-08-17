package models.persistence

import models.entities.UserType
import slick.driver.MySQLDriver.api._

/**
  * Created by Matija on 16.8.2016.
  */
class UserTypeTable(tag : Tag) extends Table[UserType](tag, "user_type") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def * = (name,id) <> (UserType.tupled, UserType.unapply)
}