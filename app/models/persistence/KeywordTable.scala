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
  def * = (keyword,active,id) <> ((Keyword.apply _).tupled, Keyword.unapply)
}
