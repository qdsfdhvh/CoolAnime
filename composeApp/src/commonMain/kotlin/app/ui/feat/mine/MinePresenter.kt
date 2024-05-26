package app.ui.feat.mine

import androidx.compose.runtime.Composable
import app.route.MineScreen
import com.seiko.anime.compiler.annotations.CircuitInject
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@CircuitInject(MineScreen::class)
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
