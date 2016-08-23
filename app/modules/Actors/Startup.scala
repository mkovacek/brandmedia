package modules.Actors

import javax.inject.{Inject, Named, Singleton}
import akka.actor.ActorRef
import models.daos.KeywordDAO
import modules.Twitter.StartStream
import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext}

/**
  * Created by Matija on 23.8.2016..
  * Class that on application startup life cycle run actor to start stream
  * for each active word
  */
@Singleton
class Startup @Inject()(@Named("stream-actor") streamActor: ActorRef, keywordDAO: KeywordDAO)(implicit ec: ExecutionContext) {
  println("Startup")
  val activeKeywords = Await.result(keywordDAO.allActive(), 1 second)
  activeKeywords.map{keyword =>
    streamActor ! StartStream(keyword.id,keyword.keyword,keyword.id.toString + "-" + keyword.userId.toString)
  }
}