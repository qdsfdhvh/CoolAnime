package app.ui.feat.home

import androidx.compose.runtime.Immutable
import app.ui.component.state.UiState
import app.ui.component.voyager.VoyagerUiEvent
import app.ui.component.voyager.VoyagerUiState
import domain.model.AnimeShell
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class HomeUiState(
  val recentUpdatesState: UiState<ImmutableList<AnimeShell>>,
  val eventSink: (HomeUiEvent) -> Unit,
) : VoyagerUiState

sealed interface HomeUiEvent : VoyagerUiEvent {
  data object RefreshRecentUpdates : HomeUiEvent
  data class GotoDetail(val item: AnimeShell) : HomeUiEvent
  data object GotoCounter : HomeUiEvent
}
