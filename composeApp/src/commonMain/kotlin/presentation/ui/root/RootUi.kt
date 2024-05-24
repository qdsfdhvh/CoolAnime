package presentation.ui.root

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.slack.circuit.backstack.SaveableBackStack
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.overlay.ContentWithOverlays
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuitx.gesturenavigation.GestureNavigationDecoration
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import presentation.route.HomeScreen
import presentation.route.MineScreen
import presentation.route.ScheduleScreen
import presentation.widget.FloatingNavigationBar
import presentation.widget.HazeScaffold
import presentation.widget.LottieIcon

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun RootUi(
  backStack: SaveableBackStack,
  navigator: Navigator,
  windowSizeClass: WindowSizeClass,
  modifier: Modifier = Modifier,
) {
  val navigationType = remember(windowSizeClass) {
    NavigationType.forWindowSizeSize(windowSizeClass)
  }

  val rootScreen by remember(backStack) {
    derivedStateOf { backStack.last().screen }
  }

  val isShowBar by remember(backStack) {
    derivedStateOf {
      backStack.first().screen.let {
        it is HomeScreen || it is ScheduleScreen || it is MineScreen
      }
    }
  }

  val hazeState = remember { HazeState() }
  val navigationItems = remember { buildHomeNavigationItems() }

  HazeScaffold(
    modifier = modifier,
    hazeState = hazeState,
    bottomBar = {
      if (isShowBar && navigationType == NavigationType.BOTTOM_NAVIGATION) {
        HomeNavigationBar(
          selectScreen = rootScreen,
          navigationItems = navigationItems,
          onNavigationItemClicked = {
            navigator.resetRootIfDifferent(
              screen = it,
              saveState = true,
              restoreState = true,
            )
          },
          modifier = Modifier
            .padding(horizontal = 24.dp)
            .padding(bottom = 8.dp)
            .windowInsetsPadding(WindowInsets.navigationBars)
            .hazeChild(
              state = hazeState,
              style = HazeMaterials.regular(),
              shape = MaterialTheme.shapes.extraLarge,
            )
            .fillMaxWidth(),
        )
      }
    },
  ) {
    Row(Modifier.fillMaxSize()) {
      if (isShowBar) {
        when (navigationType) {
          NavigationType.RAIL -> {
            HomeNavigationRail(
              selectScreen = rootScreen,
              navigationItems = navigationItems,
              onNavigationItemClicked = {
                navigator.resetRootIfDifferent(it, saveState = true, restoreState = true)
              },
              modifier = Modifier.fillMaxHeight(),
            )
            VerticalDivider()
          }

          NavigationType.PERMANENT_DRAWER -> {
            HomeNavigationDrawer(
              selectScreen = rootScreen,
              navigationItems = navigationItems,
              onNavigationItemClicked = {
                navigator.resetRootIfDifferent(it, saveState = true, restoreState = true)
              },
              modifier = Modifier.fillMaxHeight(),
            )
          }

          NavigationType.BOTTOM_NAVIGATION -> Unit
        }
      }
      ContentWithOverlays(
        modifier = Modifier.weight(1f).fillMaxHeight(),
      ) {
        NavigableCircuitContent(
          backStack = backStack,
          navigator = navigator,
          decoration = remember(navigator) {
            GestureNavigationDecoration(onBackInvoked = navigator::pop)
          },
          modifier = Modifier.fillMaxSize(),
        )
      }
    }
  }
}

@Composable
private fun HomeNavigationBar(
  selectScreen: Screen,
  navigationItems: ImmutableList<HomeNavigationItem>,
  onNavigationItemClicked: (Screen) -> Unit,
  modifier: Modifier = Modifier,
) {
  FloatingNavigationBar(
    modifier = modifier,
    containerColor = Color.Transparent,
  ) {
    navigationItems.fastForEach { item ->
      val isSelected = selectScreen == item.screen
      NavigationBarItem(
        icon = {
          LottieIcon(
            iconPath = item.iconPath,
            selected = isSelected,
          )
        },
        label = {
          Text(item.label)
        },
        selected = isSelected,
        onClick = { onNavigationItemClicked(item.screen) },
      )
    }
  }
}

@Composable
private fun HomeNavigationRail(
  selectScreen: Screen,
  navigationItems: ImmutableList<HomeNavigationItem>,
  onNavigationItemClicked: (Screen) -> Unit,
  modifier: Modifier = Modifier,
) {
  NavigationRail(modifier) {
    navigationItems.fastForEach { item ->
      val isSelected = selectScreen == item.screen
      NavigationRailItem(
        icon = {
          LottieIcon(
            iconPath = item.iconPath,
            selected = isSelected,
          )
        },
        alwaysShowLabel = false,
        label = { Text(text = item.label) },
        selected = isSelected,
        onClick = { onNavigationItemClicked(item.screen) },
      )
    }
  }
}

@Composable
private fun HomeNavigationDrawer(
  selectScreen: Screen,
  navigationItems: ImmutableList<HomeNavigationItem>,
  onNavigationItemClicked: (Screen) -> Unit,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier
      .windowInsetsPadding(WindowInsets.safeContent)
      .padding(16.dp)
      .widthIn(max = 280.dp),
  ) {
    navigationItems.fastForEach { item ->
      val isSelected = selectScreen == item.screen
      NavigationDrawerItem(
        icon = {
          LottieIcon(
            iconPath = item.iconPath,
            selected = isSelected,
          )
        },
        label = { Text(item.label) },
        selected = isSelected,
        onClick = { onNavigationItemClicked(item.screen) },
      )
    }
  }
}

@Immutable
private data class HomeNavigationItem(
  val screen: Screen,
  val label: String,
  val iconPath: String,
)

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

private enum class NavigationType {
  BOTTOM_NAVIGATION,
  RAIL,
  PERMANENT_DRAWER,
  ;

  companion object {
    fun forWindowSizeSize(windowSizeClass: WindowSizeClass): NavigationType = when {
      windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact -> BOTTOM_NAVIGATION
      windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact -> BOTTOM_NAVIGATION
      windowSizeClass.widthSizeClass == WindowWidthSizeClass.Medium -> RAIL
      else -> PERMANENT_DRAWER
    }
  }
}

private fun Navigator.resetRootIfDifferent(
  screen: Screen,
  saveState: Boolean = false,
  restoreState: Boolean = false,
) {
  val backStack = peekBackStack()
  if (backStack.size > 1 || backStack.lastOrNull() != screen) {
    resetRoot(screen, saveState, restoreState)
  }
}
