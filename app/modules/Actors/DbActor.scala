package modules.Actors

import javax.inject.Inject

import akka.actor.{Actor, Props}
import models.daos.MentionDAO
import models.entities.Mention

/**
  * Created by Matija on 28.8.2016..
  */

object DbActor {
  def props = Props[DbActor]
}

class DbActor @Inject() (mentionDAO: MentionDAO) extends Actor {
  def receive: Receive = {
    case Mention(text,created,userName,userUsername,userLocation,userImage,retweetCount,favoriteCount,keywordId,0L) => mentionDAO.save(Mention(text,created,userName,userUsername,userLocation,userImage,retweetCount,favoriteCount,keywordId))
  }
}
