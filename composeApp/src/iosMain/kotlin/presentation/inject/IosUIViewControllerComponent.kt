package presentation.inject

import com.seiko.anime.cool.MainViewController
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import platform.UIKit.UIViewController

@ActivityScope
@Component
abstract class IosUIViewControllerComponent(
  @Component val applicationComponent: IosApplicationComponent,
) : UiComponent {
  abstract val uiViewControllerFactory: () -> UIViewController

  @ActivityScope
  @Provides
  fun uiViewController(bind: MainViewController): UIViewController = bind()

  companion object
}
