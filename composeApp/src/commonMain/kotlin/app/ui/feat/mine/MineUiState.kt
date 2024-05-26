package app.ui.feat.mine

import androidx.compose.runtime.Immutable
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState

@Immutable
data class MineUiState(
  val eventSink: (MineEvent) -> Unit,
) : CircuitUiState

sealed interface MineEvent : CircuitUiEvent
