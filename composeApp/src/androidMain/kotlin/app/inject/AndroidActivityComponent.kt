package app.inject

import android.app.Activity
import app.ui.feat.root.RootContent
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@ActivityScope
@Component
abstract class AndroidActivityComponent(
  @get:Provides val activity: Activity,
  @Component val applicationComponent: AndroidApplicationComponent,
) : ActivityComponent {
  abstract val rootContent: RootContent
  companion object
}
