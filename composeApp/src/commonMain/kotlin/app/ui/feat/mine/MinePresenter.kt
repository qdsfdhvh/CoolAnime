package app.ui.feat.mine

import androidx.compose.runtime.Composable
import app.route.MineScreen
import app.ui.component.voyager.ProviderNavigator
import com.seiko.anime.compiler.annotations.BindPresenter
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@BindPresenter(MineScreen::class)
@Inject
@Composable
fun MinePresenter(
  @Assisted navigator: ProviderNavigator,
): MineUiState {
  return MineUiState(
    eventSink = { event ->
    },
  )
}
