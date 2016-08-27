package models.Other.Statistics

import play.api.libs.json.Json

/**
  * Created by Matija on 24.8.2016..
  * Model for statistics
  */
case class Statistics(name: Option[String], value: Int)
object Statistics {
  implicit val statisticsWrites = Json.writes[Statistics]
  implicit val statisticsReads = Json.reads[Statistics]
}
