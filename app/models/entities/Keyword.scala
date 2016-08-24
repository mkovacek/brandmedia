package models.entities

import play.api.libs.json.Json

/**
  * Created by Matija on 16.8.2016..
  * Keyword entity
  */
case class Keyword(keyword: String,id: Long = 0L)
object Keyword {
  implicit val keywordWrites = Json.writes[Keyword]
  implicit val keywordReads = Json.reads[Keyword]
}