package app.ui.feat.detail

import androidx.compose.runtime.Immutable
import app.ui.component.voyager.VoyagerUiEvent
import app.ui.component.voyager.VoyagerUiState

@Immutable
data class DetailUiState(
  val id: Long,
  val content: String,
  val eventSink: (DetailUiEvent) -> Unit,
) : VoyagerUiState

sealed interface DetailUiEvent : VoyagerUiEvent {
  data object Request : DetailUiEvent
}
