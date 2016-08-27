package models.entities

import play.api.libs.json.Json

/**
  * Created by Matija on 16.8.2016..
  * Mention entity
  */
case class Mention(text: Option[String], created: Option[String], userName: Option[String], userUsername: Option[String], userLocation: Option[String],
                   userImage: Option[String], retweetCount: Option[Long], favoriteCount: Option[Long], keywordId: Long,id:Option[Long] = Some(0L))

object Mention {
  implicit val keywordWrites = Json.writes[Mention]
  implicit val keywordReads = Json.reads[Mention]
}