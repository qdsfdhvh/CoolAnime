package app.ui.feat.mine

import androidx.compose.runtime.Immutable
import app.ui.component.navigation.runtime.BaseUiEvent
import app.ui.component.navigation.runtime.BaseUiState

@Immutable
data class MineUiState(
  val eventSink: (MineEvent) -> Unit,
) : BaseUiState

sealed interface MineEvent : BaseUiEvent
