package models.daos

import javax.inject.Inject
import models.entities.Mention
import models.persistence.MentionTable
import modules.Twitter.Tweet
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
  def save(tweet: Tweet, keywordId: Long) = Future {
    val newMention = Mention(
      tweet.text,
      tweet.created_at,
      tweet.user.name,
      tweet.user.screen_name,
      tweet.user.location,
      tweet.user.profile_background_image_url_https,
      tweet.retweet_count,
      tweet.favorite_count,
      keywordId
    )
    db.run(mention += newMention)
  }

  /*
  * Paginated mention fetching by keyword
  * */
  def fetchMentions(keywordId: Long, offset: Int, size: Int): Future[Seq[Mention]] = {
    db.run(mention.filter(_.keywordId === keywordId).sortBy(_.id.desc).drop(offset).take(size).result)
  }

}
