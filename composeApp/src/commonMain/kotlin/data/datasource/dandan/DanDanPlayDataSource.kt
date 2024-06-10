package data.datasource.dandan

import data.datasource.dandan.model.BangumiListResponse
import data.util.AppHttpClientFactory
import data.util.globalJson
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import me.tatarka.inject.annotations.Inject

@Inject
class DanDanPlayDataSource(
  httpClientFactory: AppHttpClientFactory,
) {
  private val httpClient by lazy {
    httpClientFactory {
      defaultRequest {
        url(BASE_URL)
        contentType(ContentType.Application.Json)
      }
      install(ContentNegotiation) {
        json(globalJson)
      }
    }
  }

  /**
   * 获取新番列表
   */
  suspend fun shin(): BangumiListResponse {
    return httpClient.get("shin") {
      url {
        parameter("filterAdultContent", false)
      }
    }.body()
  }

  companion object {
    private const val BASE_URL = "https://api.dandanplay.net/api/v2/bangumi/"
  }
}
