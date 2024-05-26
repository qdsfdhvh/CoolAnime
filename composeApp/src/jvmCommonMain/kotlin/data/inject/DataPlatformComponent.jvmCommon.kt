package data.inject

import app.inject.ApplicationScope
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import me.tatarka.inject.annotations.Provides

actual interface DataPlatformComponent {
  @ApplicationScope
  @Provides
  fun provideHttpClientEngine(): HttpClientEngine {
    return OkHttp.create()
  }
}
