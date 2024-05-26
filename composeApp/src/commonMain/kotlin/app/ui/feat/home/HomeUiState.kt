package app.ui.feat.home

import androidx.compose.runtime.Immutable
import app.ui.component.navigation.runtime.BaseUiEvent
import app.ui.component.navigation.runtime.BaseUiState
import app.ui.component.state.UiState
import domain.model.AnimeShell
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class HomeUiState(
  val recentUpdatesState: UiState<ImmutableList<AnimeShell>>,
  val eventSink: (HomeUiEvent) -> Unit,
) : BaseUiState

sealed interface HomeUiEvent : BaseUiEvent {
  data object RefreshRecentUpdates : HomeUiEvent
  data class GotoDetail(val item: AnimeShell) : HomeUiEvent
  data object GotoCounter : HomeUiEvent
}
