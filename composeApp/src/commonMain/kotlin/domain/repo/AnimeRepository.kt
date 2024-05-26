package domain.repo

import domain.model.AnimeShell

interface AnimeRepository {
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
  ): List<AnimeShell>
}
