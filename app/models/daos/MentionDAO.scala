package models.daos

import javax.inject.Inject

import models.Other.Statistics.Statistics
import models.Other.Twitter.Tweet
import models.entities.{Keyword, Mention}
import models.persistence.MentionTable
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Matija on 23.8.2016..
  * Class responsible to get data from a data source (Mentions)
  */
class MentionDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) {
  /*
  * Config
  * */
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db
  import dbConfig.driver.api._

  /*
  * table-query objects
  * */
  private val mention = TableQuery[MentionTable]

  /*
  * Method save new mention
  * */
  def save(keywords: Seq[Keyword], tweet: Tweet) = Future {
    keywords.filter(k => tweet.text.get.toLowerCase.contains(k.keyword.toLowerCase)).map{ keyword =>
      val newMention = Mention(
        tweet.text,
        tweet.created_at,
        tweet.user.name,
        tweet.user.screen_name,
        tweet.place.country,
        tweet.user.profile_background_image_url_https,
        tweet.retweet_count,
        tweet.favorite_count,
        keyword.id
      )
      db.run(mention += newMention)
    }
  }

  /*
  * Paginated mention fetching by keyword
  * */
  def fetchMentions(keywordId: Long, offset: Int, size: Int): Future[Seq[Mention]] = {
    db.run(mention.filter(_.keywordId === keywordId).sortBy(_.id.desc).drop(offset).take(size).result)
  }

  /*
  * Statistics by user mentions
  * */
  def statisticsByUser(keywordId: Long, size: Int): Future[Seq[Statistics]] = {
    val query = mention.filter(_.keywordId === keywordId).filter(_.userUsername =!= "").filter(_.userUsername =!= " ").groupBy(_.userUsername).map{
      case (s, results) => (s -> results.length)
    }

    val action = for(
      result <- query.take(size).result
    ) yield {
      result.map{ row =>
        Statistics(row._1, row._2)
      }
    }
    db.run(action)
  }

  /*
  * Statistics by countrie mentions
  * */
  def statisticsByCountries(keywordId: Long, size: Int): Future[Seq[Statistics]] = {
    val query = mention.filter(_.keywordId === keywordId).filter(_.userLocation.isDefined).filter(_.userLocation =!= "").filter(_.userLocation =!= " ").groupBy(_.userLocation).map{
      case (s, results) => (s -> results.length)
    }

    val action = for(
      result <- query.take(size).result
    ) yield {
      result.map{ row =>
        Statistics(row._1,row._2)
      }
    }
    db.run(action)
  }

}
