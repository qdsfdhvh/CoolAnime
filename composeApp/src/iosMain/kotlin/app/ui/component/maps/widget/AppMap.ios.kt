package app.ui.component.maps.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

// TODO: Support GoogleMaps on iOS
@Composable
actual fun AppMap(
  mapState: AppMapState,
  modifier: Modifier,
) {
}

actual typealias AppMapState = Any

@Composable
actual fun rememberAppMapState(): AppMapState {
  return ""
}
