package data.datasource.dandan.mapper

import data.datasource.dandan.model.BangumiIntro
import domain.model.AnimeShell

fun BangumiIntro.toAnime(): AnimeShell {
  return AnimeShell(
    id = animeId,
    name = animeTitle,
    imageUrl = imageUrl,
    summary = "",
    status = if (isOnAir) "连载中" else "已完结",
  )
}
