package data.repo

import com.fleeksoft.ksoup.Ksoup
import data.repo.datasource.YhmgoDataSource
import data.util.HtmlParser
import domain.model.AnimeShell
import domain.repo.AnimeRepository
import me.tatarka.inject.annotations.Inject

@Inject
class AnimeRepositoryImpl(
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
  ): List<AnimeShell> {
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
    return HtmlParser.parseAnimeList(doc)
  }

  override suspend fun getWeeklySchedule(): List<List<AnimeShell>> {
    val html = yhmgoDataSource.getHomePage()
    val doc = Ksoup.parse(html)
    return HtmlParser.parseWeeklySchedule(doc)
  }
}
