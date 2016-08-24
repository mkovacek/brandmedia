package modules.Actors

import javax.inject.Inject

import akka.actor.{Actor, Props}
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

class StreamActor @Inject() (mentionDAO: MentionDAO, ws: WSClient, killSwitch: KillSwitch, conf: Configuration) extends Actor{
   def receive: Receive = {
     case StartStream(keywords, keywordsString) => Twitter(ws,killSwitch,conf,mentionDAO).startStream(keywords,keywordsString)
     case RestartStream(keywords, keywordsString) => Twitter(ws,killSwitch,conf,mentionDAO).restartStream(keywords,keywordsString)
     case StopStream(streamId) => Twitter(ws,killSwitch,conf,mentionDAO).stopStream(streamId)
   }

}
