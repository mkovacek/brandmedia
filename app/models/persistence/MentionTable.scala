package models.persistence

import java.sql.Date

import models.entities.Mention
import slick.driver.MySQLDriver.api._

/**
  * Created by Matija on 16.8.2016..
  */
class MentionTable(tag : Tag) extends Table[Mention](tag, "mention") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def text = column[String]("text")
  def created = column[Date]("created")
  def userName = column[String]("user_name")
  def userUserName = column[String]("user_username")
  def userLocation = column[String]("user_location")
  def userImage = column[String]("user_image")
  def retweetCount = column[Long]("retweet_count")
  def favoriteCount = column[Long]("favorite_count")
  def keywordId = column[Long]("keyword_id")
  def * = (text,created,userName,userUserName,userLocation,userImage,retweetCount,favoriteCount,keywordId,id) <> (Mention.tupled, Mention.unapply)
  def keyword = foreignKey("mention_keyword_fk",keywordId,TableQuery[KeywordTable])(_.id)
}
