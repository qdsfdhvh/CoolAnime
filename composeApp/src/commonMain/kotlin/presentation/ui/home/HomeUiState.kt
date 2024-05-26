package presentation.ui.home

import androidx.compose.runtime.Immutable
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import domain.model.AnimeShell
import kotlinx.collections.immutable.ImmutableList
import presentation.component.state.UiState

@Immutable
data class HomeUiState(
  val recentUpdatesState: UiState<ImmutableList<AnimeShell>>,
  val eventSink: (HomeUiEvent) -> Unit,
) : CircuitUiState

sealed interface HomeUiEvent : CircuitUiEvent {
  data object RefreshRecentUpdates : HomeUiEvent
  data class GotoDetail(val item: AnimeShell) : HomeUiEvent
  data object GotoCounter : HomeUiEvent
}
