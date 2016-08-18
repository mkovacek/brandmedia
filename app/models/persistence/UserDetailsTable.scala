package models.persistence

import models.entities.UserDetails
import slick.driver.MySQLDriver.api._
/**
  * Created by Matija on 16.8.2016.
  * Class for UserDetails entity <=> DB user_details table connection
  */
class UserDetailsTable(tag : Tag) extends Table[UserDetails](tag, "user_details") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def surname = column[String]("surname")
  def * = (name,surname,id) <> (UserDetails.tupled, UserDetails.unapply)
}
