package presentation.ui.schedule

import androidx.compose.runtime.Composable
import com.seiko.anime.compiler.annotations.BindPresenter
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import presentation.route.ScheduleScreen

@BindPresenter(ScheduleScreen::class)
@Inject
class SchedulePresenter(
  @Assisted private val navigator: Navigator,
) : Presenter<ScheduleUiState> {
  @Composable
  override fun present(): ScheduleUiState {
    return ScheduleUiState(
      eventSink = { event ->
      },
    )
  }
}
