package data.remote.inject

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import me.tatarka.inject.annotations.Provides
import presentation.inject.ApplicationScope

actual interface NetworkPlatformComponent {
  @ApplicationScope
  @Provides
  fun provideHttpClientEngine(): HttpClientEngine {
    return OkHttp.create()
  }
}
