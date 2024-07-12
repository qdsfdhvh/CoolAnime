package app.ui.feat.map

import androidx.compose.runtime.Composable
import app.route.MapScreen
import app.ui.component.maps.widget.rememberAppMapState
import app.ui.component.voyager.ProviderNavigator
import com.seiko.anime.compiler.annotations.BindPresenter
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@BindPresenter(MapScreen::class)
@Inject
@Composable
fun MapPresenter(
  @Assisted navigator: ProviderNavigator,
): MapUiState {
  return MapUiState(
    mapState = rememberAppMapState(),
  )
}
