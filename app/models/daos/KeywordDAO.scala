package models.daos

import javax.inject.Inject

import models.entities.{Keyword, KeywordUserKeyword, UserKeyword}
import models.persistence.{KeywordTable, UserKeywordTable}
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Matija on 22.8.2016..
  * Class responsible to get data from a data source (Keyword)
  */
class KeywordDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider){
  /*
  * Config
  * */
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db
  import dbConfig.driver.api._

  /*
  * table-query objects
  * */
  private val keyword = TableQuery[KeywordTable]
  private val userKeyword = TableQuery[UserKeywordTable]
  private val ACTIVE = 1

  /*
  * Method fetch all keywords by user id
  * */
  def all(id: Long): Future[Seq[KeywordUserKeyword]] = {
    val query = for {
      (user, keyword) <- userKeyword.filter(_.userId === id) join keyword on (_.keywordId === _.id)
    } yield (keyword,user.active)

    val action = for {
        results <- query.result
    } yield {
        results.map{ row =>
          KeywordUserKeyword(row._1.keyword,row._1.id, row._2)
        }
    }
    db.run(action)
  }

  /*
  * Method fetch all active keywords for stream
  * */
  def allStream(): Future[Seq[Keyword]] = {
    db.run(keyword.filter(_.active === ACTIVE).result)
  }

  /*
  * Method fetch keyword by name
  * */
  def findByName(name: String): Future[Option[Keyword]] = {
    db.run(keyword.filter(_.keyword === name).result.headOption)
  }

  /*
  * Method fetch userkeyword by keywordsid
  * */
  def fetchByKewordId(keywordId: Long): Future[Seq[UserKeyword]] = {
    db.run(userKeyword.filter(_.keywordId === keywordId).result)
  }

  /*
  * Method fetch all active keywords by user id
  * */
  def active(id: Long): Future[Seq[KeywordUserKeyword]] = {
    val query = for {
      (user, keyword) <- userKeyword.filter(_.userId === id).filter(_.active === ACTIVE) join keyword on (_.keywordId === _.id)
    } yield (keyword,user.active)

    val action = for {
      results <- query.result
    } yield {
      results.map{ row =>
        KeywordUserKeyword(row._1.keyword,row._1.id, row._2)
      }
    }
    db.run(action)
  }

  /*
  * Method save new keyword and return new keyword
  * */
  def saveKeyword(newKeyword: Keyword): Future[Keyword] = {
    val insert = keyword returning keyword.map(_.id) into ((keyword, id) => keyword.copy(id = id))
    db.run(insert += newKeyword)
  }

  /*
  * Method save keyword to user_keyword
  * */
  def saveUserKeyword(newUserKeyword: UserKeyword) = Future {
    db.run(userKeyword += newUserKeyword)
  }

  /*
  * Method update user keyword active status
  * */
  def updateUserKeywordStatus(keywordId: Long, active: Int, userId: Long) = Future {
    db.run(userKeyword.filter(_.userId === userId).filter(_.keywordId === keywordId).map(k => (k.active)).update(active))
  }

  /*
  * Method update keyword active status
  * */
  def updateKeywordStatus(keywordId: Long, active: Int) = Future {
    db.run(keyword.filter(_.id === keywordId).map(k => (k.active)).update(active))
  }

}
