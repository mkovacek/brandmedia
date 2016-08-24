package modules.Twitter

import models.entities.Keyword

/**
  * Created by Matija on 24.8.2016..
  * Case class for actor messaging
  */
case class RestartStream(keywords: Seq[Keyword], keywordsString: String)
