package app.ui.feat.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import app.route.HomeScreen
import app.route.MineScreen
import app.route.ScheduleScreen
import app.ui.component.navigation.runtime.NavHost
import app.ui.component.navigation.runtime.Navigator
import app.ui.component.navigation.screen.Screen
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun RootUi(
  navigator: Navigator,
  windowSizeClass: WindowSizeClass,
  modifier: Modifier = Modifier,
) {
  val rootScreen by remember(navigator.backStacks) {
    derivedStateOf { navigator.backStacks.first().screen }
  }

  val isShowBar by remember(navigator.backStacks) {
    derivedStateOf {
      navigator.backStacks.last().screen.let {
        it is HomeScreen || it is ScheduleScreen || it is MineScreen
      }
    }
  }

  val navigationItems = remember { buildHomeNavigationItems() }

  NavigationScaffold(
    windowSizeClass = windowSizeClass,
    navigationItems = navigationItems,
    onNavigationItemClicked = {
      navigator.resetRootIfDifferent(
        screen = it.screen,
        saveState = true,
        restoreState = true,
      )
    },
    selectScreen = rootScreen,
    isShowBar = isShowBar,
    onBack = {
      navigator.pop()
    },
    modifier = modifier,
  ) {
    NavHost(
      navigator = navigator,
      modifier = Modifier.fillMaxSize(),
    )
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
      screen = ScheduleScreen,
      label = "日程",
      iconPath = "files/ic_calendar.json",
    ),
    HomeNavigationItem(
      screen = MineScreen,
      label = "我的",
      iconPath = "files/ic_my.json",
    ),
  )
}

private fun Navigator.resetRootIfDifferent(
  screen: Screen,
  saveState: Boolean = false,
  restoreState: Boolean = false,
) {
  // TODO: support saveState & restoreState
  replaceAll(screen)
  // val backStack = peekBackStack()
  // if (backStack.size > 1 || backStack.lastOrNull() != screen) {
  //   resetRoot(screen, saveState, restoreState)
  // }
}
