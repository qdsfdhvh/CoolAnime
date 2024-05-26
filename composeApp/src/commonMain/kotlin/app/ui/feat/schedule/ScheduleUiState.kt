package app.ui.feat.schedule

import androidx.compose.runtime.Immutable
import app.ui.component.navigation.runtime.BaseUiEvent
import app.ui.component.navigation.runtime.BaseUiState

@Immutable
data class ScheduleUiState(
  val eventSink: (ScheduleEvent) -> Unit,
) : BaseUiState

sealed interface ScheduleEvent : BaseUiEvent
