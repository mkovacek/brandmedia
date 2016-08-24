package models.persistence

import models.entities.UserKeyword
import slick.driver.MySQLDriver.api._

/**
  * Created by Matija on 24.8.2016..
  * Class for UserKeyword entity <=> DB user_keyword table connection
  */
class UserKeywordTable(tag : Tag) extends Table[UserKeyword](tag, "user_keyword") {
  def userId = column[Long]("user_id")
  def keywordId = column[Long]("keyword_id")
  def active = column[Int]("active")
  def * = (userId,keywordId,active) <> ((UserKeyword.apply _).tupled, UserKeyword.unapply)
  def user = foreignKey("user_keyword_user_fk",userId,TableQuery[UserTable])(_.id)
  def keyword = foreignKey("user_keyword_keywords_fk",keywordId,TableQuery[KeywordTable])(_.id)
}
