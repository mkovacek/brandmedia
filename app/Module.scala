import akka.routing.RoundRobinPool
import com.google.inject.AbstractModule
import modules.Actors.{Startup, StreamActor}
import play.api.libs.concurrent.AkkaGuiceSupport

/*
 * Play will automatically use any class called `Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 * Run Startup class
 * */
class Module extends AbstractModule with AkkaGuiceSupport {
  override def configure() = {
    bindActor[StreamActor]("stream-actor", RoundRobinPool(4).props)
    bind(classOf[Startup]).asEagerSingleton()
  }
}
