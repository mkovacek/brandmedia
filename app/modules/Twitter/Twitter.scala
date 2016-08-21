package modules.Twitter

import play.api.libs.oauth._
import javax.inject.{Inject,Singleton}
import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, KillSwitches}
import play.api.libs.ws._
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}
import org.json4s._
import org.json4s.native.JsonMethods._

/**
  * Created by Matija on 20.8.2016..
  */

@Singleton
class Twitter @Inject() (name: String,ws: WSClient, killSwitch: KillSwitch) (implicit ec:ExecutionContext) {
  private val twitterConsumerKey = "do213KmGfaxKfBDRpp0bL7ebp"
  private val twitterConsumerSecret = "6SF6zxkSIewbKUaxGYQO4zGL1eCcN0TWaA2q8z77DJhDYnksvL"
  private val accessTokenKey = "166725756-RTkRyo5nW7CSgMEMpgxYnHxI8R81R4ZsbtT2NQBd"
  private val accessTokenSecret = "kmNgVXtJFbiMEtUgzS83PDfdnppwPi2LA8SBQoBA8UFmV"
  private val url = "https://stream.twitter.com/1.1/statuses/filter.json"

  val consumerKey: ConsumerKey = ConsumerKey(twitterConsumerKey, twitterConsumerSecret)
  val requestToken: RequestToken = RequestToken(accessTokenKey, accessTokenSecret)

  val urlPath = "https://api.twitter.com/1.1/search/tweets.json"
  implicit val formats = DefaultFormats

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  def getTweets(query: String): Unit ={
    val sharedKillSwitch = KillSwitches.shared(query)
    killSwitch.add(query,sharedKillSwitch)
    ws.url(url)
        .sign(OAuthCalculator(consumerKey, requestToken))
        .withQueryString(("track",query))
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
                case Success(tweet) =>
                  println("-----")
                  println(tweet.text)
                case Failure(e) =>
                  println("-----")
                  println(e.getStackTrace)
              }
          }
        }
  }

  def stopStream(name: String) ={
    val sharedKillSwitch = killSwitch.get(name)
    sharedKillSwitch.shutdown()
  }
}

object Twitter {
  def apply(name: String, ws: WSClient,killSwitch: KillSwitch)(implicit ec:ExecutionContext): Twitter = new Twitter(name,ws,killSwitch)
}
