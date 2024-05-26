package app.ui.feat.schedule

import androidx.compose.runtime.Immutable
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState

@Immutable
data class ScheduleUiState(
  val eventSink: (ScheduleEvent) -> Unit,
) : CircuitUiState

sealed interface ScheduleEvent : CircuitUiEvent
