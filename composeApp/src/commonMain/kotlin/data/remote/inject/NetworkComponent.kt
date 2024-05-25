package data.remote.inject

interface NetworkComponent : NetworkPlatformComponent {

  // @ApplicationScope
  // @Provides
  // fun provideHttpClient(
  //   engine: HttpClientEngine,
  //   json: Json,
  //   logger: Logger,
  // ): HttpClient {
  //   return HttpClient(engine) {
  //     install(ContentNegotiation) {
  //       json(json)
  //     }
  //     install(Logging) {
  //       this.level = LogLevel.ALL
  //       this.logger = object : KtorLogger {
  //         override fun log(message: String) {
  //           logger.d("ktor") { message }
  //         }
  //       }
  //     }
  //   }
  // }
}
