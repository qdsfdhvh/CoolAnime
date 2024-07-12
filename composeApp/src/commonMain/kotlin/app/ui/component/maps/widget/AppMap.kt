package app.ui.component.maps.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun AppMap(
  mapState: AppMapState,
  modifier: Modifier = Modifier,
)

expect class AppMapState

@Composable
expect fun rememberAppMapState(): AppMapState
