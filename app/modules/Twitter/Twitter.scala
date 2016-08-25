package modules.Twitter

import play.api.libs.oauth._
import javax.inject.Inject

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, KillSwitches}
import models.Other.Twitter.Tweet
import models.daos.MentionDAO
import models.entities.Keyword
import play.api.libs.ws._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}
import org.json4s._
import org.json4s.native.JsonMethods._
import play.api.Configuration

/**
  * Created by Matija on 20.8.2016..
  */

class Twitter @Inject() (ws: WSClient, killSwitch: KillSwitch, conf: Configuration, mentionDAO: MentionDAO) (implicit ec:ExecutionContext) {
  private val url = "https://stream.twitter.com/1.1/statuses/filter.json"
  private val consumerKey: ConsumerKey = ConsumerKey(conf.underlying.getString("twitter.consumerKey"), conf.underlying.getString("twitter.consumerSecret"))
  private val requestToken: RequestToken = RequestToken(conf.underlying.getString("twitter.accessTokenKey"), conf.underlying.getString("twitter.accessTokenSecret"))
  implicit val formats = DefaultFormats
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  def startStream(keywords: Seq[Keyword], keywordsString: String) = Future {
    val sharedKillSwitch = KillSwitches.shared(keywordsString)
    killSwitch.add(sharedKillSwitch)
    ws.url(url)
      .sign(OAuthCalculator(consumerKey,requestToken))
      .withQueryString(("track",keywordsString))
      .withMethod("POST")
      .stream()
      .map { response =>
        if(response.headers.status == 200){
          response.body
            .via(sharedKillSwitch.flow)
            .scan("")((acc, curr) => if (acc.contains("\r\n")) curr.utf8String else acc + curr.utf8String)
            .filter(_.contains("\r\n"))
            .map(json => Try(parse(json).extract[Tweet]))
            .runForeach {
              case Success(tweet) => mentionDAO.save(keywords,tweet)
              case Failure(e) => ""
            }
        }
      }
  }

  def stopStream(streamId: String) =  {
    killSwitch.get().shutdown()
  }

  def restartStream(keywords: Seq[Keyword], keywordsString: String) = Future {
    this.stopStream(keywordsString)
    this.startStream(keywords,keywordsString)
  }
}

object Twitter {
  def apply(ws: WSClient,killSwitch: KillSwitch,conf: Configuration,mentionDAO: MentionDAO)(implicit ec:ExecutionContext): Twitter = new Twitter(ws,killSwitch,conf,mentionDAO)
}
