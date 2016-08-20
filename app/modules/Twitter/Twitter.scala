package modules.Twitter

import play.api.libs.oauth._
import javax.inject.Inject

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import play.api.libs.ws._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}
import org.json4s._
import org.json4s.native.JsonMethods._

/**
  * Created by Matija on 20.8.2016..
  */
class Twitter @Inject() (ws: WSClient) (implicit ec:ExecutionContext) {
  private val twitterConsumerKey = "do213KmGfaxKfBDRpp0bL7ebp"
  private val twitterConsumerSecret = "6SF6zxkSIewbKUaxGYQO4zGL1eCcN0TWaA2q8z77DJhDYnksvL"
  private val accessTokenKey = "166725756-RTkRyo5nW7CSgMEMpgxYnHxI8R81R4ZsbtT2NQBd"
  private val accessTokenSecret = "kmNgVXtJFbiMEtUgzS83PDfdnppwPi2LA8SBQoBA8UFmV"
  private val url = "https://stream.twitter.com/1.1/statuses/filter.json?track=messi"

  val consumerKey: ConsumerKey = ConsumerKey(twitterConsumerKey, twitterConsumerSecret)
  val requestToken: RequestToken = RequestToken(accessTokenKey, accessTokenSecret)

  val urlPath = "https://api.twitter.com/1.1/search/tweets.json?q=messi"
  implicit val formats = DefaultFormats

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  //val tweets=



/*  val futureResponse: Future[StreamedResponse] =
    ws.url(url)
      .sign(OAuthCalculator(consumerKey, requestToken))
      .withMethod("POST")
      .stream()

  val bytesReturned: Future[Long] = futureResponse.flatMap {
    res =>
      // Count the number of bytes returned
      res.body.runWith(Sink.fold[Long, ByteString](0L){ (total, bytes) =>
        total + bytes.length
      })
  }*/

  ws.url(url)
    .sign(OAuthCalculator(consumerKey, requestToken))
    .withMethod("POST")
    .stream()
    .map { response =>
      if(response.headers.status == 200){
        response.body
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

    /*case StreamedResponse(response, body) =>
      // Check that the response was successful
      if (response.status == 200) {
        // Get the content type
        val contentType = response.headers.get("Content-Type").flatMap(_.headOption).getOrElse("application/octet-stream")
        response.headers.get("Content-Length") match {
         case Some(Seq(length)) => println(HttpEntity.Streamed(body, Some(length.toLong), Some(contentType)))
         case _ => println(body.)
           //chunked(body).as(contentType)
        }
        //HttpEntity.Streamed(body, Some(length.toLong), Some(contentType))
        /*body.runWith(Sink.fold[String,ByteString]{
          (String,bytestring)
        })*/
        //Ok.chunked(body).as(contentType)
        // If there's a content length, send that, otherwise return the body chunked
        /*response.headers.get("Content-Length") match {
          case Some(Seq(length)) =>
            Ok.sendEntity(HttpEntity.Streamed(body, Some(length.toLong), Some(contentType)))
          case _ =>
            Ok.chunked(body).as(contentType)
        }*/
      } else {
        println("400")
      }
  }*/
}
