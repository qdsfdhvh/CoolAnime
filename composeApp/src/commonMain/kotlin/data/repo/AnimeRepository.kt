package data.repo

import com.fleeksoft.ksoup.Ksoup
import data.remote.service.YhmgoService
import data.util.HtmlParser
import kotlinx.coroutines.withContext
import me.tatarka.inject.annotations.Inject
import presentation.inject.ApplicationScope
import presentation.util.AppCoroutineDispatchers

@ApplicationScope
@Inject
class AnimeRepository(
  private val appCoroutineDispatchers: AppCoroutineDispatchers,
  private val yhmgoService: YhmgoService,
) {

  suspend fun filterAnimeBy(
    region: String? = null,
    genre: String? = null,
    letter: String? = null,
    year: String? = null,
    status: String? = null,
    label: String? = null,
    orderBy: String? = null,
    pageIndex: Int = 0,
    pageSize: Int = 24,
  ) = withContext(appCoroutineDispatchers.io) {
    val html = yhmgoService.filterAnimeBy(
      region = region,
      genre = genre,
      letter = letter,
      year = year,
      label = label,
      status = status,
      orderBy = orderBy,
      pageIndex = pageIndex,
      pageSize = pageSize,
    )
    val doc = Ksoup.parse(html)
    HtmlParser.parseAnimeList(doc)
  }
}
