package modules.Panel

import javax.inject.{Inject, Named}

import akka.actor.ActorRef
import models.Other.Twitter.RestartStream
import models.daos.{KeywordDAO, MentionDAO, UserDAO}
import models.entities.{Keyword, User, UserDetails, UserKeyword}
import modules.Security.AuthenticatedRequest
import modules.Twitter.KillSwitch
import play.api.libs.json.{JsObject, JsValue, Json}
import play.api.mvc.AnyContent
import pdi.jwt._
import play.api.{Configuration, Logger}
import play.api.libs.ws.WSClient

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Matija on 19.8.2016..
  * Panel handler
  */
class PanelHandler @Inject()(userDAO: UserDAO, keywordDAO: KeywordDAO, mentionDAO: MentionDAO, ws: WSClient, killSwitch: KillSwitch, conf: Configuration, @Named("stream-actor") streamActor: ActorRef) {
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
    val uniqueKeyword = Await.result(keywordDAO.findByName(keyword),1 second)
    val (newStream,word) = uniqueKeyword match {
      case Some(keyword) => (false,keyword)
      case None => (true,Await.result(keywordDAO.saveKeyword(Keyword(keyword,ACTIVE)), 1 second))
    }
    Await.result(keywordDAO.saveUserKeyword(UserKeyword(user.id,word.id,ACTIVE)), 1 second)
    val keywordList = Await.result(keywordDAO.all(user.id), 1 second)
    Logger.info("save keyword: "+keyword)
    if(newStream) this.startNewStream()
    Json.toJson(keywordList)
  }

  /*
  * Method returns user keywords
  * */
  def getAllKeywords(request: AuthenticatedRequest[AnyContent]): JsValue = {
    val user = request.jwtSession.getAs[User]("user").get
    Json.toJson(Await.result(keywordDAO.all(user.id), 1 second))
  }

  /*
  * Method sreturn user active keywords
  * */
  def getActiveKeywords(request: AuthenticatedRequest[AnyContent]): JsValue = {
    val user = request.jwtSession.getAs[User]("user").get
    Json.toJson(Await.result(keywordDAO.active(user.id), 1 second))
  }

  /*
  * Method update keyword active status and start stream
  * */
  def updateAndStartStream(request: AuthenticatedRequest[JsValue]): JsObject = {
    val user = request.jwtSession.getAs[User]("user").get
    val json = request.body.result.get.\("keyword")
    val keywordId = json.\("keywordId").as[Long]
    val active = json.\("active").as[Int]
    val userKeywords = Await.result(keywordDAO.fetchByKewordId(keywordId),1 second)
    if(userKeywords.forall(_.active == 0)){
      Await.result(keywordDAO.updateKeywordStatus(keywordId,active), 1 second)
      this.startNewStream()
    }
    Logger.info("update and start: "+keywordId)
    keywordDAO.updateUserKeywordStatus(keywordId,active,user.id)
    Json.obj("success" -> "successfully updated keyword status")
  }

  /*
  * Method update keyword active status and stop stream
  * */
  def updateAndStopStream(request: AuthenticatedRequest[JsValue]): JsObject = {
    val user = request.jwtSession.getAs[User]("user").get
    val json = request.body.result.get.\("keyword")
    val keywordId = json.\("keywordId").as[Long]
    val active = json.\("active").as[Int]
    Await.result(keywordDAO.updateUserKeywordStatus(keywordId,active,user.id), 1 second)
    val userKeywords = Await.result(keywordDAO.fetchByKewordId(keywordId),1 second)
    if(userKeywords.forall(_.active == 0)){
      Await.result(keywordDAO.updateKeywordStatus(keywordId,active), 1 second)
      this.startNewStream()
    }
    Logger.info("update and stop: "+keywordId)
    Json.obj("success" -> "successfully updated keyword status")
  }



  /*
  * Restart stream with added new tracking keywords
  * */
  private def startNewStream() = Future {
    val activeKeywords = Await.result(keywordDAO.allStream(), 1 second)
    val keywordString = activeKeywords.map(_.keyword).mkString(",")
    streamActor ! RestartStream(activeKeywords,keywordString)
  }


  /*
  * Get keyword mentions
  * */
  def getMentions(request: AuthenticatedRequest[JsValue]): JsObject = {
    val json = request.body.result.get.\("data")
    val keywordId = json.\("keywordId").as[Long]
    val offset = json.\("offset").as[Int]
    val size = json.\("size").as[Int]
    val mentions = Json.toJson(Await.result(mentionDAO.fetchMentions(keywordId, offset, size), 1 second))
    Json.obj("meta" -> Json.obj("offset" -> size), "mentions" -> mentions)
  }

  /*
  * Get analytics
  * */
  def getAnalytics(request: AuthenticatedRequest[JsValue]): JsObject = {
    val json = request.body.result.get.\("data")
    val keywordId = json.\("keywordId").as[Long]
    val size = json.\("size").as[Int]
    val analytics = for {
      byUsers <- mentionDAO.statisticsByUser(keywordId,size)
      byCountrie <-mentionDAO.statisticsByCountries(keywordId,size)
    } yield (byUsers,byCountrie)
    val result = Await.result(analytics, 10 second)
    Json.obj("users" -> result._1, "countries" -> result._2)
  }


}
