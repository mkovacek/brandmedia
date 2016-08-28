package modules.Actors

import javax.inject.{Inject, Named}

import akka.actor.{Actor, ActorRef, Props}
import models.Other.Twitter.{StreamPair, Tweet}
import models.entities.{Keyword, Mention}
import play.api.Logger


/**
  * Created by Matija on 28.8.2016..
  */

object MentionActor {
  def props = Props[MentionActor]
}

class MentionActor @Inject() (@Named("db-actor") dbActor: ActorRef) extends Actor{
  def receive: Receive = {
    case StreamPair(keywords,tweet) => pairKeywordWithTweet(keywords,tweet) match {
      case Some(mention) => dbActor ! mention
      case None => Logger.info("no match")
    }
  }

  def pairKeywordWithTweet(keywords: Seq[Keyword], tweet: Tweet): Option[Mention] = {
    val keyword = keywords.filter(k => tweet.text.toLowerCase.contains(k.keyword.toLowerCase)).headOption
    keyword match {
      case Some(keyword) =>
        Some(Mention(
        tweet.text,
        tweet.created_at,
        tweet.user.name,
        tweet.user.screen_name,
        tweet.user.location.getOrElse("Unknown country"),
        tweet.user.profile_background_image_url_https,
        tweet.retweet_count,
        tweet.favorite_count,
        keyword.id
      ))
      case None => None
    }
  }
}
