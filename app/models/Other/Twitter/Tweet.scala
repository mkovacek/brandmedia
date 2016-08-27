package models.Other.Twitter

/**
  * Created by Matija on 20.8.2016..
  */
case class Tweet(
                  contributors: Option[Seq[Contributor]],
                  coordinates: Option[Coordinates],
                  created_at: Option[String],
                  current_user_retweet: Option[Map[String, String]],
                  entities: Entities,
                  favorite_count: Option[Long],
                  favorited: Option[Boolean],
                  filter_level: Option[String],
                  id: Option[Long],
                  id_str: Option[String],
                  in_reply_to_screen_name: Option[String],
                  in_reply_to_status_id: Option[Long],
                  in_reply_to_status_id_str: Option[String],
                  in_reply_to_user_id: Option[Long],
                  in_reply_to_user_id_str: Option[String],
                  lang: Option[String],
                  place: Place,
                  possibly_sensitive: Option[Boolean],
                  quoted_status_id: Option[Long],
                  quoted_status_id_str: Option[String],
                  quoted_status: Option[Tweet],
                  scopes: Option[Map[String, String]],
                  retweet_count: Option[Long],
                  retweeted: Option[Boolean],
                  retweeted_status: Option[Tweet],
                  source: Option[String],
                  text: Option[String],
                  truncated: Option[Boolean],
                  user: Users,
                  withheldCopyright: Option[Boolean],
                  withheldInCountries: Option[Seq[String]],
                  withheldScope: Option[String]
                )
case class Users(
                  contributors_enabled: Option[Boolean],
                  created_at: Option[String],
                  default_profile: Option[Boolean],
                  default_profile_image: Option[Boolean],
                  description: Option[String],
                  entities: Option[Entities],
                  favourites_count: Option[Int],
                  follow_request_sent: Option[Boolean],
                  following: Option[Boolean],
                  followers_count: Option[Int],
                  friends_count: Option[Int],
                  geo_enabled: Option[Boolean],
                  id: Option[Long],
                  id_str: Option[String],
                  is_translator: Option[Boolean],
                  lang: Option[String],
                  listed_count: Option[Int],
                  location: Option[String],
                  name: Option[String],
                  notifications: Option[Boolean],
                  profile_background_color: Option[String],
                  profile_background_image_url: Option[String],
                  profile_background_image_url_https: Option[String],
                  profile_background_tile: Option[Boolean],
                  profile_banner_url: Option[String],
                  profile_image_url: Option[String],
                  profile_image_url_https: Option[String],
                  profile_link_color: Option[String],
                  profile_sidebar_border_color: Option[String],
                  profile_sidebar_fill_color: Option[String],
                  profile_text_color: Option[String],
                  profile_use_background_image: Option[Boolean],
                  `protected`: Option[Boolean],
                  screen_name: Option[String],
                  show_all_inline_media: Option[Boolean],
                  status: Tweets,
                  statuses_count: Option[Int],
                  time_zone: Option[String],
                  url: Option[String],
                  utc_offset: Option[Int],
                  verified: Option[Boolean],
                  withheld_in_countries: Option[String],
                  withheld_scope: Option[String]
                )
case class Tweets(
                   contributors: Option[Seq[Contributor]],
                   coordinates: Option[Coordinates],
                   created_at: Option[String],
                   current_user_retweet: Option[Map[String, String]],
                   entities: Option[Entities],
                   favorite_count: Option[Int],
                   favorited: Option[Boolean],
                   filter_level: Option[String],
                   id: Option[Long],
                   id_str: Option[String],
                   in_reply_to_screen_name: Option[String],
                   in_reply_to_status_id: Option[Long],
                   in_reply_to_status_id_str: Option[String],
                   in_reply_to_user_id: Option[Long],
                   in_reply_to_user_id_str: Option[String],
                   lang: Option[String],
                   place: Option[Place],
                   possibly_sensitive: Option[Boolean],
                   quoted_status_id: Option[Long],
                   quoted_status_id_str: Option[String],
                   quoted_status: Option[Tweet],
                   scopes: Option[Map[String, String]],
                   retweet_count: Option[Int],
                   retweeted: Option[Boolean],
                   retweeted_status: Option[Tweet],
                   source: Option[String],
                   text: Option[String],
                   truncated: Option[Boolean],
                   user: Option[Users],
                   withheld_copyright: Option[Boolean],
                   withheld_in_countries: Option[Seq[String]],
                   withheld_scope: Option[String]
                 )
case class Place(
                  attributes: Option[Map[String, String]],
                  bounding_box: Option[BoundingBox],
                  country: Option[String],
                  country_code: Option[String],
                  full_name: Option[String],
                  id: Option[String],
                  name: Option[String],
                  place_type:Option[String],
                  url: Option[String]
                )
case class Media(
                  display_url: Option[String],
                  expanded_url: Option[String],
                  id: Option[Long],
                  id_str: Option[String],
                  indices: Option[Seq[Int]],
                  media_url: Option[String],
                  media_url_https: Option[String],
                  sizes: Option[Sizes],
                  source_status_id: Option[Long],
                  source_status_id_str: Option[String],
                  `type`:Option[String],
                  url: Option[String]
                )
case class BoundingBox(coordinates: Seq[Seq[Seq[Float]]], `type`: Option[String])
case class Contributor(id: Option[Long], id_str: Option[String], screen_name: Option[String])
case class Coordinates(coordinates: Option[Seq[Float]], `type`: Option[String])
case class Entities(hashtags:Option[Seq[Hashtag]], media: Option[Seq[Media]], urls: Option[Seq[Url]], user_mentions: Option[Seq[UserMention]])
case class Sizes(thumb: Option[Size], large: Option[Size], medium: Option[Size], small:Option[Size])
case class Size(h: Option[Int], w: Option[Int], resize: Option[String])
case class Hashtag(indices: Option[Seq[Int]], text: Option[String])
case class Url(display_url: Option[String], expanded_url: Option[String], indices: Option[Seq[Int]], url: Option[String])
case class UserMention(id: Option[Long], id_str: Option[String], indices: Option[Seq[Int]], name:Option[String], screen_name: Option[String])
