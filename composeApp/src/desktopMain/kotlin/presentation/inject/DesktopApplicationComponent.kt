package presentation.inject

import me.tatarka.inject.annotations.Component

@ApplicationScope
@Component
abstract class DesktopApplicationComponent : ApplicationComponent {
  companion object
}
