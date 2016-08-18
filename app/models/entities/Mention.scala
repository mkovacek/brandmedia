package models.entities

import java.sql.Date


/**
  * Created by Matija on 16.8.2016..
  * Mention entity
  */
case class Mention(text: String, created: Date, userName: String, userUsername: String, userLocation: String,
                   userImage: String, retweetCount: Long, favoriteCount: Long, keywordId: Long,id: Long = 0L)
