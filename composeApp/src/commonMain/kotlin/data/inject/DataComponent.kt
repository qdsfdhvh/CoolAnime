package data.inject

import app.inject.ApplicationScope
import app.util.logging.AppLogger
import data.repo.AnimeRepositoryImpl
import data.util.AppHttpClientFactory
import domain.repo.AnimeRepository
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import me.tatarka.inject.annotations.Provides

interface DataComponent : DataPlatformComponent {

  @ApplicationScope
  @Provides
  fun provideAppHttpClientFactory(
    engine: HttpClientEngine,
    logger: AppLogger,
  ): AppHttpClientFactory {
    return AppHttpClientFactory(engine) {
      install(Logging) {
        this.level = LogLevel.BODY
        this.logger = object : Logger {
          override fun log(message: String) {
            logger.d("ktor") { message }
          }
        }
      }
    }
  }

  @ApplicationScope
  @Provides
  fun provideAnimeRepository(impl: AnimeRepositoryImpl): AnimeRepository = impl
}
