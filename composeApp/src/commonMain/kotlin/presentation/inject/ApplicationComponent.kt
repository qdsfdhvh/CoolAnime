package presentation.inject

import data.remote.NetworkComponent
import me.tatarka.inject.annotations.Provides
import presentation.util.logging.KermitLogger
import presentation.util.logging.Logger

interface ApplicationComponent : NetworkComponent {
  @ApplicationScope
  @Provides
  fun provideLogger(): Logger = KermitLogger()
}
