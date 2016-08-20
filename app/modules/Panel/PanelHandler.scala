package modules.Panel

import javax.inject.Inject
import models.daos.UserDAO
import models.entities.{UserDetails}
import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by Matija on 19.8.2016..
  * Panel handler
  */
class PanelHandler @Inject()(userDAO: UserDAO) {
  /*
  * Method fetch user details
  * */
  def getUserDetails(id: Long): UserDetails = {
    Await.result(userDAO.findUserDetailsById(id), 1 second).get
  }
}
