package data.repo

import com.fleeksoft.ksoup.Ksoup
import data.datasource.dandan.DanDanPlayDataSource
import data.datasource.dandan.mapper.toAnime
import data.datasource.yhdm.YhmgoDataSource
import data.util.HtmlParser
import data.util.exception.HttpException
import domain.model.AnimeShell
import domain.repo.AnimeRepository
import me.tatarka.inject.annotations.Inject

@Inject
class AnimeRepositoryImpl(
  private val danDanPlayDataSource: DanDanPlayDataSource,
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
    val response = danDanPlayDataSource.shin()
    if (!response.success) {
      throw HttpException(response.errorMessage)
    }
    return response.bangumiList.map { it.toAnime() }
  }

  override suspend fun getWeeklySchedule(): List<List<AnimeShell>> {
    val html = yhmgoDataSource.getHomePage()
    val doc = Ksoup.parse(html)
    return HtmlParser.parseWeeklySchedule(doc)
  }
}
