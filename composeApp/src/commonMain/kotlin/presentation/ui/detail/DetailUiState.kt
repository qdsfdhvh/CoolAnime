package presentation.ui.detail

import androidx.compose.runtime.Immutable
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState

@Immutable
data class DetailUiState(
  val id: Long,
  val content: String,
  val eventSink: (DetailUiEvent) -> Unit,
) : CircuitUiState

sealed interface DetailUiEvent : CircuitUiEvent {
  data object Back : DetailUiEvent
  data object Request : DetailUiEvent
}
