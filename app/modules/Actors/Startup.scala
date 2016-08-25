package modules.Actors

import javax.inject.{Inject, Named, Singleton}

import akka.actor.ActorRef
import models.Other.Twitter.StartStream
import models.daos.KeywordDAO

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext}

/**
  * Created by Matija on 23.8.2016..
  * Class that on application startup life cycle run actor to start stream
  * for each active word
  */
@Singleton
class Startup @Inject()(@Named("stream-actor") streamActor: ActorRef, keywordDAO: KeywordDAO)(implicit ec: ExecutionContext) {
   val activeKeywords = Await.result(keywordDAO.allStream(), 1 second)
   val keywordString = activeKeywords.map(_.keyword).mkString(",")
   if(activeKeywords.nonEmpty) streamActor ! StartStream(activeKeywords,keywordString)
}
