package app.ui.component.maps.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapState
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.rememberMapState

@Composable
actual fun AppMap(
  mapState: AppMapState,
  modifier: Modifier,
) {
  MapboxMap(
    mapState = mapState,
    mapViewportState = rememberAppMapViewportState(),
    modifier = modifier,
  )
}

actual typealias AppMapState = MapState

@Composable
actual fun rememberAppMapState(): AppMapState {
  return rememberMapState()
}

@Composable
private fun rememberAppMapViewportState(): MapViewportState {
  return rememberMapViewportState {
    setCameraOptions {
      zoom(7.5)
      center(CityLocations.Tokyo)
    }
  }
}

private object CityLocations {
  val Tokyo: Point get() = Point.fromLngLat(139.6917, 35.6895)
  // val Nagoya: Point get() = Point.fromLngLat(136.9066, 35.1815)
}
