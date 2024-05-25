package data.util

import com.fleeksoft.ksoup.nodes.Document
import com.fleeksoft.ksoup.select.Elements
import domain.model.AnimeShell

object HtmlParser {

  /**
   * 适配页面：
   * - https://www.yhmgo.com/s_all
   * - https:/www.yhmgo.com/list/
   */
  fun parseAnimeList(document: Document): List<AnimeShell> =
    buildList {
      val lis: Elements = document.select("div.lpic > ul > li")

      for (li in lis) {
        val id = li.child(0).attr("href")
          .filter { it.isDigit() }
          .toInt()
        val imgUrl = li.child(0).child(0).attr("src")
          .let { if (it.startsWith("//")) "https:$it" else it }

        val name = li.child(1).child(0).ownText()

        val episodeInfo = li.child(2).child(0).ownText()

        val latestEpisode = episodeInfo.extractLatestEpisode()

        val status = episodeInfo.run {
          when {
            contains("完结|全集|-".toRegex()) -> "已完结"
            contains("PV") -> "未播放"
            isEmpty() -> "未知"
            else -> "连载中"
          }
        }

        add(
          AnimeShell(
            id = id,
            name = name,
            imageUrl = imgUrl,
            latestEpisode = latestEpisode,
            status = status,
          ),
        )
      }
    }

  /**
   * 适配：
   * 第24集(完结) \ 22:00 第2集(每周一22:00更新)
   * 第1集(每周一更新) \ [OVA 01-04] \[全集]
   * 第3话(完结) \ 第OVA1话 \ [OVA 01-02+SP]
   * 第06集(完结) \ [TV 01-12+SP01-06]
   */
  private fun String.extractLatestEpisode() =
    this.substringAfter('第')
      .substringBefore('+')
      .substringAfter('-')
      .substringBefore('(')
      .filter { it.isDigit() }
      .takeIf { it.isNotEmpty() }
      ?.toInt()
      ?: 1
}
