import akka.routing.RoundRobinPool
import com.google.inject.AbstractModule
import modules.Actors.{Startup, StreamActor, StreamRestartActor}
import play.api.libs.concurrent.AkkaGuiceSupport

/*
 * Play will automatically use any class called `Module` that is in
 * the root package. Run Stream actor and startup class on startup life cycle
 * */
class Module extends AbstractModule with AkkaGuiceSupport {
  override def configure() = {
    bindActor[StreamActor]("stream-actor", RoundRobinPool(4).props)
    bindActor[StreamRestartActor]("streamRestart-actor", RoundRobinPool(4).props)
    bind(classOf[Startup]).asEagerSingleton()
  }
}
