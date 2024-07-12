package app.ui.feat.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.route.MapScreen
import app.ui.component.maps.widget.AppMap
import com.seiko.anime.compiler.annotations.BindUi

@BindUi(MapScreen::class)
@Composable
fun MapUi(
  state: MapUiState,
  modifier: Modifier,
) {
  AppMap(
    modifier = modifier.fillMaxSize(),
  )
}
