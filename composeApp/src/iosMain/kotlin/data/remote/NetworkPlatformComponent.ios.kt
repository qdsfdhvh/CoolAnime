package data.remote

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import me.tatarka.inject.annotations.Provides
import presentation.inject.ApplicationScope

actual interface NetworkPlatformComponent {
  @ApplicationScope
  @Provides
  fun provideHttpClientEngine(): HttpClientEngine {
    return Darwin.create()
  }
}
