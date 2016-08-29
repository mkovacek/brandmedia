package modules.Twitter

import play.api.libs.oauth._
import javax.inject.{Inject, Named, Singleton}

import akka.actor.{ActorRef, ActorSystem}
import akka.stream._
import models.Other.Twitter.{StartStream,RestartStream, StreamPair, Tweet}
import models.daos.MentionDAO
import models.entities.Keyword
import play.api.libs.ws._
import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}
import org.json4s._
import org.json4s.native.JsonMethods._
import play.api.{Configuration, Logger}

/**
  * Created by Matija on 20.8.2016..
  */
@Singleton
class Twitter @Inject() (ws: WSClient, conf: Configuration, mentionDAO: MentionDAO, @Named("streamRestart-actor") streamRestartActor: ActorRef, @Named("mention-actor") mentionActor: ActorRef) (implicit ec:ExecutionContext) {
  private val url = "https://stream.twitter.com/1.1/statuses/filter.json"
  private val consumerKey: ConsumerKey = ConsumerKey(conf.underlying.getString("twitter.consumerKey"), conf.underlying.getString("twitter.consumerSecret"))
  private val requestToken: RequestToken = RequestToken(conf.underlying.getString("twitter.accessTokenKey"), conf.underlying.getString("twitter.accessTokenSecret"))
  implicit val formats = DefaultFormats
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  var time = 0
  val sharedKillSwitch = KillSwitches.shared("twitter")



  def startStream(keywords: Seq[Keyword], keywordsString: String) = Future {
    if(time > 300000) time = 60000
    time = time + 10000
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
            .idleTimeout(30.seconds)
            .scan("")((acc, curr) => if (acc.contains("\r\n")) curr.utf8String else acc + curr.utf8String)
            .filter(_.contains("\r\n"))
            .map ( json => Try(parse(json).extract[Tweet]) )
            .runForeach {
              case Success(tweet) => mentionActor ! StreamPair(keywords,tweet)
              case Failure(e) => Logger.info("Failure: "+e.getMessage)
            }
            .onComplete {
              case Success(_) =>
                Logger.info("Done")
                this.stopStream()
                Thread.sleep(time)
                streamRestartActor ! StartStream(keywords,keywordsString)
              case Failure(e) =>
                Logger.info("Failed with "+e.getMessage)
                this.stopStream()
                Thread.sleep(time)
                streamRestartActor ! StartStream(keywords,keywordsString)
            }
        }
        if(response.headers.status.toString.startsWith("4")){
          Logger.info("Response 4XX : "+response.headers.status)
          response.body
            .via(sharedKillSwitch.flow)
            .scan("")((acc, curr) => if (acc.contains("\r\n")) curr.utf8String else acc + curr.utf8String)
            .filter(_.contains("\r\n"))
            .map ( json => json )
            .runForeach{
              case error: String =>
                Logger.info("error: "+error)
                this.stopStream()
                Thread.sleep(time)
                streamRestartActor ! StartStream(keywords,keywordsString)
            }
        }
      }
  }

  def stopStream() =  {
    sharedKillSwitch.shutdown()
  }

  def restartStream(keywords: Seq[Keyword], keywordsString: String) = Future {
    this.stopStream()
    this.startStream(keywords,keywordsString)
  }
}

object Twitter {
  def apply(ws: WSClient,conf: Configuration,mentionDAO: MentionDAO,@Named("streamRestart-actor") streamRestartActor: ActorRef, @Named("mention-actor") mentionActor: ActorRef)(implicit ec:ExecutionContext): Twitter = new Twitter(ws,conf,mentionDAO,streamRestartActor,mentionActor)
}
