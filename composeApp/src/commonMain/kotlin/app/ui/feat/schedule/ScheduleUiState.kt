package app.ui.feat.schedule

import androidx.compose.runtime.Immutable
import app.ui.component.state.UiState
import app.ui.component.voyager.VoyagerUiEvent
import app.ui.component.voyager.VoyagerUiState
import domain.model.AnimeShell
import kotlinx.collections.immutable.ImmutableList
import kotlinx.datetime.DayOfWeek

@Immutable
data class ScheduleUiState(
  val selectDayOfWeek: DayOfWeek,
  val weeklySchedule: UiState<ImmutableList<ImmutableList<AnimeShell>>>,
  val eventSink: (ScheduleEvent) -> Unit,
) : VoyagerUiState

sealed interface ScheduleEvent : VoyagerUiEvent {
  data object Refresh : ScheduleEvent
  data class GotoDetail(val item: AnimeShell) : ScheduleEvent
  data class SelectDayOfWeek(val dayOfWeek: DayOfWeek) : ScheduleEvent
}
