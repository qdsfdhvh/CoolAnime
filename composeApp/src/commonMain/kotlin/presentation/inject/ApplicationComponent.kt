package presentation.inject

import data.remote.inject.NetworkComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Provides
import presentation.util.AppCoroutineDispatchers
import presentation.util.logging.AppLogger
import presentation.util.logging.KermitAppLogger

interface ApplicationComponent : NetworkComponent {
  @ApplicationScope
  @Provides
  fun provideLogger(): AppLogger = KermitAppLogger()

  @ApplicationScope
  @Provides
  fun provideJson(): Json = Json {
    ignoreUnknownKeys = true
  }

  @OptIn(ExperimentalCoroutinesApi::class)
  @ApplicationScope
  @Provides
  fun provideCoroutineDispatchers() = AppCoroutineDispatchers(
    io = Dispatchers.IO,
    databaseWrite = Dispatchers.IO.limitedParallelism(1),
    databaseRead = Dispatchers.IO.limitedParallelism(4),
    computation = Dispatchers.Default,
    main = Dispatchers.Main,
  )
}
