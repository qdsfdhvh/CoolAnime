package app.ui.feat.mine

import androidx.compose.runtime.Immutable
import app.ui.component.voyager.VoyagerUiEvent
import app.ui.component.voyager.VoyagerUiState

@Immutable
data class MineUiState(
  val eventSink: (MineEvent) -> Unit,
) : VoyagerUiState

sealed interface MineEvent : VoyagerUiEvent
