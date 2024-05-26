package presentation.ui.schedule

import androidx.compose.runtime.Composable
import co.touchlab.kermit.Logger
import com.seiko.anime.compiler.annotations.CircuitInject
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import presentation.route.ScheduleScreen

@CircuitInject(ScheduleScreen::class)
@Inject
class SchedulePresenter(
  @Assisted private val navigator: Navigator,
) : Presenter<ScheduleUiState> {
  init {
    Logger.d("SchedulePresenter") { "init" }
  }

  @Composable
  override fun present(): ScheduleUiState {
    return ScheduleUiState(
      eventSink = { event ->
      },
    )
  }
}
