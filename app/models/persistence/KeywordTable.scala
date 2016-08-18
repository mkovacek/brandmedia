package models.persistence

import models.entities.Keyword
import slick.driver.MySQLDriver.api._

/**
  * Created by Matija on 16.8.2016..
  * Class for Keyword entity <=> DB keyword table connection
  */
class KeywordTable(tag : Tag) extends Table[Keyword](tag, "keyword") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def keyword = column[String]("keyword")
  def active = column[Int]("active")
  def userId = column[Long]("user_id")
  def * = (keyword,active,userId,id) <> (Keyword.tupled, Keyword.unapply)
  def user = foreignKey("keyword_user_fk",userId,TableQuery[UserTable])(_.id)
}
