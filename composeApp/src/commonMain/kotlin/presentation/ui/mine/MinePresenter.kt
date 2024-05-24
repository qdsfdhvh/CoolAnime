package presentation.ui.mine

import androidx.compose.runtime.Composable
import com.seiko.anime.compiler.annotations.BindPresenter
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import presentation.route.MineScreen

@BindPresenter(MineScreen::class)
@Inject
class MinePresenter(
  @Assisted private val navigator: Navigator,
) : Presenter<MineUiState> {
  @Composable
  override fun present(): MineUiState {
    return MineUiState(
      eventSink = { event ->
      },
    )
  }
}
