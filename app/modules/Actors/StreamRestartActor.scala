package modules.Actors

import javax.inject.{Inject, Named}

import akka.actor.{Actor, ActorRef, PoisonPill, Props}
import models.Other.Twitter.{RestartStream, StartStream}


/**
  * Created by Matija on 25.8.2016..
  */
object StreamRestartActor {
  def props = Props[StreamActor]
}

class StreamRestartActor @Inject() (@Named("stream-actor") streamActor: ActorRef) extends Actor{
  def receive: Receive = {
    case "Restart" => streamActor ! PoisonPill
    case RestartStream(keywords, keywordsString) => streamActor ! StartStream(keywords,keywordsString)
  }
}