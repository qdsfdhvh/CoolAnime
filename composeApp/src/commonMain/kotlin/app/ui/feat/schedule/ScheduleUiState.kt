package app.ui.feat.schedule

import androidx.compose.runtime.Immutable
import app.ui.component.navigation.runtime.BaseUiEvent
import app.ui.component.navigation.runtime.BaseUiState
import app.ui.component.state.UiState
import domain.model.AnimeShell
import kotlinx.collections.immutable.ImmutableList
import kotlinx.datetime.DayOfWeek

@Immutable
data class ScheduleUiState(
  val selectDayOfWeek: DayOfWeek,
  val weeklySchedule: UiState<ImmutableList<ImmutableList<AnimeShell>>>,
  val eventSink: (ScheduleEvent) -> Unit,
) : BaseUiState

sealed interface ScheduleEvent : BaseUiEvent {
  data object Refresh : ScheduleEvent
  data class GotoDetail(val item: AnimeShell) : ScheduleEvent
  data class SelectDayOfWeek(val dayOfWeek: DayOfWeek) : ScheduleEvent
}
