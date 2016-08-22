package modules.Panel

import javax.inject.Inject

import models.daos.{KeywordDAO, UserDAO}
import models.entities.{Keyword, User, UserDetails}
import modules.Security.AuthenticatedRequest
import modules.Twitter.{KillSwitch, Twitter}
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.AnyContent
import pdi.jwt._
import play.api.Configuration
import play.api.libs.ws.WSClient

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Matija on 19.8.2016..
  * Panel handler
  */
class PanelHandler @Inject()(userDAO: UserDAO,keywordDAO: KeywordDAO, ws: WSClient, killSwitch: KillSwitch, conf: Configuration) {
  /*
  * Method fetch user details
  * */
  def getUserDetails(id: Long): UserDetails = {
    Await.result(userDAO.findUserDetailsById(id), 1 second).get
  }

  /*
  * Method save user keyword
  * */
  def saveKeyword(request: AuthenticatedRequest[JsValue]): JsValue = {
    val user = request.jwtSession.getAs[User]("user").get
    val keyword = request.body.result.get.\("keyword").as[String]
    val ACTIVE = 1
    Json.toJson(Await.result(keywordDAO.save(Keyword(keyword,ACTIVE,user.id)), 1 second))
  }

  /*
  * Method save user keyword
  * */
  def getAllKeywords(request: AuthenticatedRequest[AnyContent]): JsValue = {
    val user = request.jwtSession.getAs[User]("user").get
    Json.toJson(Await.result(keywordDAO.all(user.id), 1 second))
  }

  /*
  * Method save user keyword
  * */
  def getActiveKeywords(request: AuthenticatedRequest[AnyContent]): JsValue = {
    val user = request.jwtSession.getAs[User]("user").get
    Json.toJson(Await.result(keywordDAO.active(user.id), 1 second))
  }

  /*
  * Method update keyword active status
  * */
  def updateAndStartStream(request: AuthenticatedRequest[JsValue]): JsValue = {
    val user = request.jwtSession.getAs[User]("user").get
    val keywordId = request.body.result.get.\("keywordId").as[Long]
    val active = request.body.result.get.\("active").as[Int]
    val keywordsList = keywordDAO.updateKeywordStatus(keywordId,active,user.id)

    val keyword = request.body.result.get.\("keyword").as[String]
    val streamId = keywordId.toString + "-"+user.id.toString
    this.startStream(keyword,streamId)
    Json.toJson(Await.result(keywordsList, 1 second))
  }

  def updateAndStopStream(request: AuthenticatedRequest[JsValue]): JsValue = {
    val user = request.jwtSession.getAs[User]("user").get
    val keywordId = request.body.result.get.\("keywordId").as[Long]
    val active = request.body.result.get.\("active").as[Int]
    val keywordsList = keywordDAO.updateKeywordStatus(keywordId,active,user.id)

    val streamId = keywordId.toString + "-"+user.id.toString
    this.stopStream(streamId)
    Json.toJson(Await.result(keywordsList, 1 second))
  }

  private def startStream(keyword: String, streamId: String) = {
    Twitter(ws,killSwitch,conf).getTweets(keyword,streamId)
  }

  private def stopStream(streamId: String) = {
    Twitter(ws,killSwitch,conf).stopStream(streamId)
  }
}
