package data.datasource.yhdm

import data.util.AppHttpClientFactory
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.parameters
import me.tatarka.inject.annotations.Inject

@Inject
class YhmgoDataSource(
  httpClientFactory: AppHttpClientFactory,
) {

  private val httpClient by lazy {
    httpClientFactory {
      defaultRequest {
        url(BASE_URL)
      }
    }
  }

  /**
   * @param region 地区 日本,中国,欧美,其他
   * @param genre 版本 TV,剧场版,WEB,OVA,其他
   * @param letter 首字母 A,B,C...
   * @param year 年份 2024,2023,2022...
   * @param status 状态 连载,完结,未播放
   * @param label 类型 搞笑,运动...
   * @param orderBy 排序 更新时间,名称,点击量
   */
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
  ): String {
    return httpClient.get("list/") {
      url {
        parameters {
          region?.let { append("region", it) }
          genre?.let { append("genre", it) }
          letter?.let { append("letter", it) }
          year?.let { append("year", it) }
          status?.let { append("status", it) }
          label?.let { append("label", it) }
          orderBy?.let { append("order", it) }
          append("pageindex", pageIndex.toString())
          append("pagesize", pageSize.toString())
        }
      }
    }.bodyAsText()
  }

  suspend fun getHomePage(): String {
    return httpClient.get("").bodyAsText()
  }

  companion object {
    private const val BASE_URL = "https://www.yhmgo.com/"
  }
}
