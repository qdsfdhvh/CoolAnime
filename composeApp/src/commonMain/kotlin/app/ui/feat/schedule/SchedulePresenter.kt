package app.ui.feat.schedule

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import app.route.DetailScreen
import app.route.ScheduleScreen
import app.ui.component.state.UiState
import app.ui.component.state.produceUiState
import app.ui.component.state.toUi
import app.ui.component.voyager.ProviderNavigator
import com.seiko.anime.compiler.annotations.BindPresenter
import domain.usecase.GetWeeklyScheduleUseCase
import kotlinx.datetime.DayOfWeek
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@BindPresenter(ScheduleScreen::class)
@Inject
@Composable
fun SchedulePresenter(
  @Assisted navigator: ProviderNavigator,
  getWeeklyScheduleUseCase: Lazy<GetWeeklyScheduleUseCase>,
): ScheduleUiState {
  var selectDayOfWeek by remember { mutableStateOf(DayOfWeek.MONDAY) }
  var weeklyScheduleRefreshKey by remember { mutableIntStateOf(0) }
  val weeklySchedule by produceUiState(weeklyScheduleRefreshKey) {
    if (value is UiState.Failure) {
      value = UiState.Loading
    }
    value = getWeeklyScheduleUseCase.value.invoke().toUi()
  }
  return ScheduleUiState(
    selectDayOfWeek = selectDayOfWeek,
    weeklySchedule = weeklySchedule,
    eventSink = { event ->
      when (event) {
        ScheduleEvent.Refresh -> {
          weeklyScheduleRefreshKey++
        }

        is ScheduleEvent.SelectDayOfWeek -> {
          selectDayOfWeek = event.dayOfWeek
        }

        is ScheduleEvent.GotoDetail -> {
          navigator.push(DetailScreen(event.item.id))
        }
      }
    },
  )
}
