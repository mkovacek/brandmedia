package models.Other.Twitter

import models.entities.Keyword

/**
  * Created by Matija on 23.8.2016..
  * Case class for actor messaging
  */
case class StartStream(keywords: Seq[Keyword], keywordsString: String)
