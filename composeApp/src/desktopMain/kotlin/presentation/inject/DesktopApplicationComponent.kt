package presentation.inject

import me.tatarka.inject.annotations.Component

@Component
@ApplicationScope
abstract class DesktopApplicationComponent : ApplicationComponent {
  companion object
}
