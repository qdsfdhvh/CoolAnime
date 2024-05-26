package data.repo

import app.util.AppCoroutineDispatchers
import com.fleeksoft.ksoup.Ksoup
import data.repo.datasource.YhmgoDataSource
import data.util.HtmlParser
import domain.repo.AnimeRepository
import kotlinx.coroutines.withContext
import me.tatarka.inject.annotations.Inject

@Inject
class AnimeRepositoryImpl(
  private val appCoroutineDispatchers: AppCoroutineDispatchers,
  private val yhmgoDataSource: YhmgoDataSource,
) : AnimeRepository {
  override suspend fun filterAnimeBy(
    region: String?,
    genre: String?,
    letter: String?,
    year: String?,
    status: String?,
    label: String?,
    orderBy: String?,
    pageIndex: Int,
    pageSize: Int,
  ) = withContext(appCoroutineDispatchers.io) {
    val html = yhmgoDataSource.filterAnimeBy(
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
