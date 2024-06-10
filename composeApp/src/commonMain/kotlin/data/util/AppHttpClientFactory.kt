package data.util

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine

class AppHttpClientFactory(
  private val engine: HttpClientEngine,
  private val initConfig: HttpClientConfig<*>.() -> Unit,
) {
  operator fun invoke(block: HttpClientConfig<*>.() -> Unit): HttpClient {
    return HttpClient(engine) {
      initConfig(this)
      block(this)
    }
  }
}
