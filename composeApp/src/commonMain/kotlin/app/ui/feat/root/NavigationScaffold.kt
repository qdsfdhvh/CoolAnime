package app.ui.feat.root

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import app.ui.widget.BackButton
import app.ui.widget.FloatingNavigationBar
import app.ui.widget.HazeScaffold
import app.ui.widget.LottieIcon
import com.slack.circuit.overlay.ContentWithOverlays
import com.slack.circuit.runtime.screen.Screen
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalHazeMaterialsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NavigationScaffold(
  windowSizeClass: WindowSizeClass,
  navigationItems: ImmutableList<HomeNavigationItem>,
  onNavigationItemClicked: (HomeNavigationItem) -> Unit,
  selectScreen: Screen,
  modifier: Modifier = Modifier,
  hazeState: HazeState = remember { HazeState() },
  isShowBar: Boolean = true,
  onBack: () -> Unit = {},
  content: @Composable () -> Unit,
) {
  val navigationType = remember(windowSizeClass) {
    NavigationType.forWindowSizeSize(windowSizeClass)
  }
  HazeScaffold(
    modifier = modifier,
    hazeState = hazeState,
    bottomBar = {
      if (isShowBar && navigationType == NavigationType.BOTTOM_NAVIGATION) {
        HomeNavigationBar(
          selectScreen = selectScreen,
          navigationItems = navigationItems,
          onNavigationItemClicked = onNavigationItemClicked,
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
    topBar = {
      if (!isShowBar) {
        TopAppBar(
          title = {},
          navigationIcon = {
            BackButton(onBack = onBack)
          },
        )
      }
    },
  ) {
    Row(Modifier.fillMaxSize()) {
      if (isShowBar) {
        when (navigationType) {
          NavigationType.RAIL -> {
            HomeNavigationRail(
              selectScreen = selectScreen,
              navigationItems = navigationItems,
              onNavigationItemClicked = onNavigationItemClicked,
              modifier = Modifier.fillMaxHeight(),
            )
            VerticalDivider()
          }

          NavigationType.PERMANENT_DRAWER -> {
            HomeNavigationDrawer(
              selectScreen = selectScreen,
              navigationItems = navigationItems,
              onNavigationItemClicked = onNavigationItemClicked,
              modifier = Modifier.fillMaxHeight(),
            )
          }

          NavigationType.BOTTOM_NAVIGATION -> Unit
        }
      }
      ContentWithOverlays(
        modifier = Modifier.weight(1f).fillMaxHeight(),
      ) {
        content()
      }
    }
  }
}

@Composable
private fun HomeNavigationBar(
  selectScreen: Screen,
  navigationItems: ImmutableList<HomeNavigationItem>,
  onNavigationItemClicked: (HomeNavigationItem) -> Unit,
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
        onClick = { onNavigationItemClicked(item) },
      )
    }
  }
}

@Composable
private fun HomeNavigationRail(
  selectScreen: Screen,
  navigationItems: ImmutableList<HomeNavigationItem>,
  onNavigationItemClicked: (HomeNavigationItem) -> Unit,
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
        onClick = { onNavigationItemClicked(item) },
      )
    }
  }
}

@Composable
private fun HomeNavigationDrawer(
  selectScreen: Screen,
  navigationItems: ImmutableList<HomeNavigationItem>,
  onNavigationItemClicked: (HomeNavigationItem) -> Unit,
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
        onClick = { onNavigationItemClicked(item) },
      )
    }
  }
}

@Immutable
data class HomeNavigationItem(
  val screen: Screen,
  val label: String,
  val iconPath: String,
)

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
