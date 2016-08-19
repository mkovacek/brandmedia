import javax.inject._

import play.api.http.DefaultHttpFilters
import play.filters.cors.CORSFilter
import play.filters.gzip.GzipFilter


/**
 * This class configures filters that run on every request.
 */

@Singleton
class Filters @Inject()(gzip: GzipFilter,corsFilter: CORSFilter) extends DefaultHttpFilters(gzip,corsFilter)