package presentation.inject

import android.app.Activity
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import presentation.ui.root.RootContent

@Component
@ActivityScope
abstract class AndroidActivityComponent(
  @get:Provides val activity: Activity,
  @Component val applicationComponent: AndroidApplicationComponent,
) : UiComponent {
  abstract val rootContent: RootContent
  companion object
}
