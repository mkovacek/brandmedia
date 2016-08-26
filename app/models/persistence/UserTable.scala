package models.persistence

import models.entities.User
import slick.driver.MySQLDriver.api._
//POVEZIVANJE MODELA S TABLICOM
/**
  * Created by Matija on 16.8.2016..
  * Class for User entity <=> DB user table connection
  */
class UserTable(tag : Tag) extends Table[User](tag, "user") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def email = column[String]("email")
  def password = column[String]("password")
  def active = column[Int]("active")
  def typeId = column[Long]("type_id")
  def detailsId = column[Long]("details_id")
  def * = (email,password,active,typeId,detailsId,id) <> ((User.apply _).tupled, User.unapply)
  def userType = foreignKey("user_user_type_fk",typeId,TableQuery[UserTypeTable])(_.id)
  def details = foreignKey("user_user_details_fk",detailsId,TableQuery[UserDetailsTable])(_.id)
}
