package app.inject

import androidx.compose.ui.awt.ComposeWindow
import app.ui.feat.root.RootContent
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@ActivityScope
@Component
abstract class DesktopWindowComponent(
  @get:Provides val window: ComposeWindow,
  @Component val applicationComponent: DesktopApplicationComponent,
) : ActivityComponent {
  abstract val rootContent: RootContent
  companion object
}
