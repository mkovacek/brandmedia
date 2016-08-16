package models.entities

import java.sql.Date


/**
  * Created by Matija on 16.8.2016..
  */
case class Mention(id: Long, text: String, created: Date, userName: String, userUsername: String,
                   userLocation: String, userImage: String, retweetCount: Long, favoriteCount: Long, keywordId: Long)
