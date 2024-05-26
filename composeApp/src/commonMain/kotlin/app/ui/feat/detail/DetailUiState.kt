package app.ui.feat.detail

import androidx.compose.runtime.Immutable
import app.ui.component.navigation.runtime.BaseUiEvent
import app.ui.component.navigation.runtime.BaseUiState

@Immutable
data class DetailUiState(
  val id: Int,
  val content: String,
  val eventSink: (DetailUiEvent) -> Unit,
) : BaseUiState

sealed interface DetailUiEvent : BaseUiEvent {
  data object Request : DetailUiEvent
}
