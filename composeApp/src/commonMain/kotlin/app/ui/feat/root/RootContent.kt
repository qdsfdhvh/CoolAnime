package app.ui.feat.root

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import app.route.HomeScreen
import app.route.MapScreen
import app.route.MineScreen
import app.ui.component.voyager.LocalUiScreenRegistry
import app.ui.component.voyager.UiScreen
import app.ui.component.voyager.UiScreenRegistry
import app.ui.theme.CoolAnimeTheme
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.LocalImageLoader
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import me.tatarka.inject.annotations.Inject

interface RootContent {
  @Composable
  fun Content(
    // backStack: BackStack<BackStackEntry>,
    // navigator: Navigator,
    modifier: Modifier,
  )
}

@Inject
class DefaultRootContent(
  // private val navigatorFactoryManager: Navigation,
  private val uiScreenRegistry: UiScreenRegistry,
  private val imageLoader: ImageLoader,
) : RootContent {
  @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
  @Composable
  override fun Content(
    // backStack: BackStack<BackStackEntry>,
    // navigator: Navigator,
    modifier: Modifier,
  ) {
    CompositionLocalProvider(
      LocalUiScreenRegistry provides uiScreenRegistry,
      LocalImageLoader provides imageLoader,
    ) {
      CoolAnimeTheme {
        val windowSizeClass = calculateWindowSizeClass()
        val navigationItems = remember { buildHomeNavigationItems() }
        val initialScreen = remember {
          uiScreenRegistry.createUi(navigationItems[1].screen)
        }
        Navigator(initialScreen) { navigator ->
          val provideNavigator = remember(navigator) {
            uiScreenRegistry.createProviderNavigator(navigator)
          }
          val rootScreen by remember {
            derivedStateOf { (navigator.lastItem as? UiScreen)?.provider }
          }
          NavigationScaffold(
            windowSizeClass = windowSizeClass,
            navigationItems = navigationItems,
            onNavigationItemClicked = {
              provideNavigator.pushSingle(it.screen)
            },
            selectScreen = rootScreen,
          ) {
            CurrentScreen()
          }
        }
      }
    }
  }
}

private fun buildHomeNavigationItems(): ImmutableList<HomeNavigationItem> {
  return persistentListOf(
    HomeNavigationItem(
      screen = HomeScreen,
      label = "首页",
      iconPath = "files/ic_home.json",
    ),
    HomeNavigationItem(
      screen = MapScreen,
      label = "地图",
      iconPath = "files/ic_calendar.json",
    ),
    HomeNavigationItem(
      screen = MineScreen,
      label = "我的",
      iconPath = "files/ic_my.json",
    ),
  )
}
