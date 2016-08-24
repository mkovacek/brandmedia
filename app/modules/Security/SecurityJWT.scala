package modules.Security

import models.entities.User
import pdi.jwt._
import play.api.libs.concurrent.Execution.Implicits._
import play.api.mvc.Results._
import play.api.mvc._
import scala.concurrent.Future

/**
  * Created by Matija on 18.8.2016..
  */
class AuthenticatedRequest[A](val user: User, request: Request[A]) extends WrappedRequest[A](request)


trait Secured {
  def Authenticated = AuthenticatedAction
}

object AuthenticatedAction extends ActionBuilder[AuthenticatedRequest] {
  def invokeBlock[A](request: Request[A], block: AuthenticatedRequest[A] => Future[Result]) =
    request.jwtSession.getAs[User]("user") match {
      case Some(user) => block(new AuthenticatedRequest(user, request)).map(_.refreshJwtSession(request))
      case _ => Future.successful(Unauthorized)
    }
}


