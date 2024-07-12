package app.ui.component.maps.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState

@OptIn(MapboxExperimental::class)
@Composable
actual fun AppMap(
  modifier: Modifier,
) {
  MapboxMap(
    modifier = modifier,
    mapViewportState = MapViewportState().apply {
      setCameraOptions {
        zoom(2.0)
        center(Point.fromLngLat(-98.0, 39.5))
        pitch(0.0)
        bearing(0.0)
      }
    },
  )
}
