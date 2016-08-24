package models.daos

import javax.inject.Inject
import models.entities.Keyword
import models.persistence.KeywordTable
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import scala.concurrent.Future
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


  /*
  * Method fetch all keywords by user id
  * */
  def all(id: Long): Future[Seq[Keyword]] = {
    db.run(keyword.filter(_.userId === id).sortBy(_.id.desc).result)
  }

  /*
  * Method fetch all active keywords
  * */
  def allActive(): Future[Seq[Keyword]] = {
    val ACTIVE = 1
    db.run(keyword.filter(_.active === ACTIVE).result)
  }

  /*
  * Method fetch all active keywords by user id
  * */
  def active(id: Long): Future[Seq[Keyword]] = {
    val ACTIVE = 1
    db.run(keyword.filter(_.userId === id).filter(_.active === ACTIVE).result)
  }

  /*
  * Method save new keyword and return new keyword and all keywords
  * */
  def save(newKeyword: Keyword): Future[(Keyword,Seq[Keyword])] = {
    val insert = keyword returning keyword.map(_.id) into ((keyword, id) => keyword.copy(id = id))
    for {
      savedKeyword <- db.run(insert += newKeyword)
      keywordList <- db.run(keyword.filter(_.userId === newKeyword.userId).result)
    } yield (savedKeyword,keywordList)
  }

  /*
  * Method update keyword active status
  * */
  def updateKeywordStatus(id: Long, active: Int, userId: Long) = Future {
    db.run(keyword.filter(_.id === id).map(k => (k.active)).update(active))
  }

}
