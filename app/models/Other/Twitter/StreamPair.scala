package models.Other.Twitter

import models.entities.Keyword

/**
  * Created by Matija on 28.8.2016..
  */
case class StreamPair(keywords: Seq[Keyword], tweet: Tweet)
