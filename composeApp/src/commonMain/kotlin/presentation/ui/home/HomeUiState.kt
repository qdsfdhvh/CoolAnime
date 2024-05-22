package presentation.ui.home

import androidx.compose.runtime.Immutable
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState

@Immutable
data class HomeUiState(
  val count: Int,
  val eventSink: (HomeUiEvent) -> Unit,
) : CircuitUiState

sealed interface HomeUiEvent : CircuitUiEvent {
  data object Add : HomeUiEvent
  data object Del : HomeUiEvent
}
