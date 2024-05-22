package presentation.inject

import androidx.compose.ui.awt.ComposeWindow
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import presentation.ui.root.RootContent

@Component
@ActivityScope
abstract class DesktopWindowComponent(
  @get:Provides val window: ComposeWindow,
  @Component val applicationComponent: DesktopApplicationComponent,
) : UiComponent {
  abstract val rootContent: RootContent
  companion object
}
