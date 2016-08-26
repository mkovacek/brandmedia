package modules.Actors

import javax.inject.{Inject, Named}

import akka.actor.{Actor, ActorRef, PoisonPill, Props}
import models.Other.Twitter.{RestartStream, StartStream}
import models.daos.MentionDAO
import modules.Twitter.KillSwitch
import play.api.Configuration
import play.api.libs.ws.WSClient

/**
  * Created by Matija on 25.8.2016..
  */
object StreamRestartActor {
  def props = Props[StreamActor]
}

class StreamRestartActor @Inject() (mentionDAO: MentionDAO, ws: WSClient, killSwitch: KillSwitch, conf: Configuration, @Named("stream-actor") streamActor: ActorRef) extends Actor{
  def receive: Receive = {
    case "Restart" => streamActor ! PoisonPill
    case RestartStream(keywords, keywordsString) => streamActor ! StartStream(keywords,keywordsString)
  }
}