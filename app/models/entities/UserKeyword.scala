package models.entities

import play.api.libs.json.Json

/**
  * Created by Matija on 24.8.2016..
  */
case class UserKeyword(userId: Long, keywordId: Long, active: Int)

case class KeywordUserKeyword(keyword: String, keywordId: Long, active: Int)

object KeywordUserKeyword {
  implicit val keywordUserKeywordWrites = Json.writes[KeywordUserKeyword]
  implicit val keywordUserKeywordReads = Json.reads[KeywordUserKeyword]
}