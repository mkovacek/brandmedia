package modules.Twitter

import javax.inject.Singleton
import akka.stream.SharedKillSwitch
import play.api.cache.CacheApi
import scala.collection.immutable.HashMap

/**
  * Created by Matija on 21.8.2016..
  */
@Singleton
class KillSwitch {
  var killSwitchMap: HashMap[String,SharedKillSwitch] = HashMap()

  def add(name: String,killSwitch: SharedKillSwitch) = {
    killSwitchMap += (name -> killSwitch)
    println(killSwitchMap)
  }

  def get(name: String): SharedKillSwitch = {
    val killSwitch = killSwitchMap(name)
    killSwitchMap -= name
    println(killSwitchMap)
    killSwitch
  }
}
