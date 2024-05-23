package data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Provides
import presentation.inject.ApplicationScope
import presentation.util.logging.Logger
import io.ktor.client.plugins.logging.Logger as KtorLogger

interface NetworkComponent : NetworkPlatformComponent {

  @ApplicationScope
  @Provides
  fun provideJson(): Json {
    return Json {
      ignoreUnknownKeys = true
    }
  }

  @ApplicationScope
  @Provides
  fun provideHttpClient(
    engine: HttpClientEngine,
    json: Json,
    logger: Logger,
  ): HttpClient {
    return HttpClient(engine) {
      install(ContentNegotiation) {
        json(json)
      }
      install(Logging) {
        this.level = LogLevel.ALL
        this.logger = object : KtorLogger {
          override fun log(message: String) {
            logger.d("ktor") { message }
          }
        }
      }
    }
  }
}
