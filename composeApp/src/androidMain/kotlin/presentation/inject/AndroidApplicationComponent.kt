package presentation.inject

import android.app.Application
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@ApplicationScope
@Component
abstract class AndroidApplicationComponent(
  @get:Provides val application: Application,
) : ApplicationComponent {
  companion object
}
