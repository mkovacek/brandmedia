package modules.Actors

import javax.inject.{Inject, Named}

import akka.actor.{Actor, ActorRef, Props}
import models.Other.Twitter.{RestartStream, StartStream, StopStream}
import models.daos.MentionDAO
import modules.Twitter._
import play.api.Configuration
import play.api.libs.ws.WSClient

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Matija on 23.8.2016..
  * Stream actor start streaming for active keywords
  * and stop streaming for inactive words
  */

object StreamActor {
  def props = Props[StreamActor]
}

class StreamActor @Inject() (mentionDAO: MentionDAO, ws: WSClient,conf: Configuration, @Named("streamRestart-actor") streamRestartActor: ActorRef,@Named("mention-actor") mentionActor: ActorRef) extends Actor{
   def receive: Receive = {
     case StartStream(keywords, keywordsString) => Twitter(ws,conf,mentionDAO,streamRestartActor,mentionActor).startStream(keywords,keywordsString)
     case RestartStream(keywords, keywordsString) => Twitter(ws,conf,mentionDAO,streamRestartActor,mentionActor).restartStream(keywords,keywordsString)
     case StopStream(streamId) => Twitter(ws,conf,mentionDAO,streamRestartActor,mentionActor).stopStream()
   }

}
