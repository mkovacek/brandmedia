package modules.Twitter

import javax.inject.Singleton
import akka.stream.SharedKillSwitch


/**
  * Created by Matija on 21.8.2016..
  */
@Singleton
class KillSwitch {
  var killSwitch: Vector[SharedKillSwitch] = Vector()

  def add(newKillSwitch: SharedKillSwitch) = {
    killSwitch = Vector(newKillSwitch)
    println("add "+killSwitch)
  }
  def get(): SharedKillSwitch = {
    val ks = killSwitch.head
    println("get "+ks)
    killSwitch = Vector()
    println("empty "+killSwitch)
    ks
  }
}
