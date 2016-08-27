package models.persistence

import models.entities.Mention
import slick.driver.MySQLDriver.api._

/**
  * Created by Matija on 16.8.2016..
  * Class for Mention entity <=> DB mention table connection
  */
class MentionTable(tag : Tag) extends Table[Mention](tag, "mention") {
  def id = column[Option[Long]]("id", O.PrimaryKey, O.AutoInc)
  def text = column[Option[String]]("text")
  def created = column[Option[String]]("created")
  def userName = column[Option[String]]("user_name")
  def userUserName = column[Option[String]]("user_username")
  def userLocation = column[Option[String]]("user_location")
  def userImage = column[Option[String]]("user_image")
  def retweetCount = column[Option[Long]]("retweet_count")
  def favoriteCount = column[Option[Long]]("favorite_count")
  def keywordId = column[Long]("keyword_id")
  def * = (text,created,userName,userUserName,userLocation,userImage,retweetCount,favoriteCount,keywordId,id) <> ((Mention.apply _).tupled, Mention.unapply)
  def keyword = foreignKey("mention_keyword_fk",keywordId,TableQuery[KeywordTable])(_.id)
}
