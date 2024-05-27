package app.ui.feat.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import app.ui.component.navigation.runtime.BackStack
import app.ui.component.navigation.runtime.BackStackEntry
import app.ui.component.navigation.runtime.LocalNavigation
import app.ui.component.navigation.runtime.Navigation
import app.ui.component.navigation.runtime.Navigator
import app.ui.theme.CoolAnimeTheme
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.LocalImageLoader
import me.tatarka.inject.annotations.Inject

interface RootContent {
  @Composable
  fun Content(
    backStack: BackStack<BackStackEntry>,
    navigator: Navigator,
    modifier: Modifier,
  )
}

@Inject
class DefaultRootContent(
  private val navigatorFactoryManager: Navigation,
  private val imageLoader: ImageLoader,
) : RootContent {
  @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
  @Composable
  override fun Content(
    backStack: BackStack<BackStackEntry>,
    navigator: Navigator,
    modifier: Modifier,
  ) {
    val windowSizeClass = calculateWindowSizeClass()

    CompositionLocalProvider(
      LocalNavigation provides navigatorFactoryManager,
      LocalImageLoader provides imageLoader,
    ) {
      // CircuitCompositionLocals(circuit) {
      CoolAnimeTheme {
        RootUi(
          backStack = backStack,
          navigator = navigator,
          windowSizeClass = windowSizeClass,
          modifier = modifier.fillMaxSize(),
        )
      }
      // }
    }
  }
}
