package models.entities

import play.api.libs.json.Json

/**
  * Created by Matija on 16.8.2016..
  * Mention entity
  */
case class Mention(text: String, created: String, userName: String, userUsername: String, userLocation: Option[String],
                   userImage: Option[String], retweetCount: Long, favoriteCount: Long, keywordId: Long,id: Long = 0L)

object Mention {
  implicit val keywordWrites = Json.writes[Mention]
  implicit val keywordReads = Json.reads[Mention]
}