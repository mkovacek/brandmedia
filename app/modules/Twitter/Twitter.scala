package modules.Twitter

import play.api.libs.oauth._
import javax.inject.{Inject, Named}

import akka.actor.{ActorRef, ActorSystem}
import akka.stream.{ActorMaterializer, KillSwitches}
import models.Other.Twitter.{RestartStream, Tweet}
import models.daos.MentionDAO
import models.entities.Keyword
import play.api.libs.ws._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}
import org.json4s._
import org.json4s.native.JsonMethods._
import play.api.{Configuration, Logger}

/**
  * Created by Matija on 20.8.2016..
  */

class Twitter @Inject() (ws: WSClient, killSwitch: KillSwitch, conf: Configuration, mentionDAO: MentionDAO, @Named("streamRestart-actor") streamRestartActor: ActorRef) (implicit ec:ExecutionContext) {
  private val url = "https://stream.twitter.com/1.1/statuses/filter.json"
  private val consumerKey: ConsumerKey = ConsumerKey(conf.underlying.getString("twitter.consumerKey"), conf.underlying.getString("twitter.consumerSecret"))
  private val requestToken: RequestToken = RequestToken(conf.underlying.getString("twitter.accessTokenKey"), conf.underlying.getString("twitter.accessTokenSecret"))
  implicit val formats = DefaultFormats
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  var time = 0

  def startStream(keywords: Seq[Keyword], keywordsString: String) = Future {
    if(time > 300000) time = 60000
    time = time + 60000
    val sharedKillSwitch = KillSwitches.shared(keywordsString)
    killSwitch.add(sharedKillSwitch)
    ws.url(url)
      .sign(OAuthCalculator(consumerKey,requestToken))
      .withQueryString(("track",keywordsString))
      .withMethod("POST")
      .stream()
      .map { response =>
        Logger.info(keywordsString+" : "+response.headers.status)
        if(response.headers.status == 200){
          response.body
            .via(sharedKillSwitch.flow)
            .scan("")((acc, curr) => if (acc.contains("\r\n")) curr.utf8String else acc + curr.utf8String)
            .filter(_.contains("\r\n"))
            .map { json =>
              if ((json.contains("disconnect") && json.contains("code")) || (json.contains("warning") && json.contains("code") )) Logger.info("json: " + json)
              Try(parse(json).extract[Tweet])
            }
            .runForeach {
              case Success(tweet) =>
                Logger.info("success: new tweet")
                mentionDAO.save(keywords,tweet)
              case Failure(e) => Logger.info("Failure: "+e.getMessage)
              case other => Logger.info("Other: "+other.toString)
            }
        }
        if(response.headers.status == 420){
          this.stopStream()
          Thread.sleep(time)
          streamRestartActor !  RestartStream(keywords,keywordsString)
        }
      }
  }

  def stopStream() =  {
    killSwitch.get().shutdown()
  }

  def restartStream(keywords: Seq[Keyword], keywordsString: String) = Future {
    this.stopStream()
    this.startStream(keywords,keywordsString)
  }
}

object Twitter {
  def apply(ws: WSClient,killSwitch: KillSwitch,conf: Configuration,mentionDAO: MentionDAO,@Named("streamRestart-actor") streamRestartActor: ActorRef)(implicit ec:ExecutionContext): Twitter = new Twitter(ws,killSwitch,conf,mentionDAO,streamRestartActor)
}
