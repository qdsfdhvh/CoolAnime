package presentation.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.seiko.anime.compiler.annotations.BindPresenter
import com.slack.circuit.retained.produceRetainedState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import data.repo.AnimeRepository
import domain.model.AnimeShell
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import presentation.component.state.UiState
import presentation.component.state.toUi
import presentation.route.DetailScreen
import presentation.route.HomeScreen

@BindPresenter(HomeScreen::class)
@Inject
class HomePresenter(
  @Assisted private val navigator: Navigator,
  private val animeRepository: Lazy<AnimeRepository>,
) : Presenter<HomeUiState> {
  @Composable
  override fun present(): HomeUiState {
    var recentUpdatesRefreshCount by remember { mutableIntStateOf(0) }
    val recentUpdatesState by produceRetainedState<UiState<ImmutableList<AnimeShell>>>(
      initialValue = UiState.Loading,
      key1 = recentUpdatesRefreshCount,
    ) {
      if (value is UiState.Failure) {
        value = UiState.Loading
      }
      value = runCatching {
        animeRepository.value.filterAnimeBy(region = "日本", pageIndex = 0).toImmutableList()
      }.toUi()
    }
    return HomeUiState(
      recentUpdatesState = recentUpdatesState,
      eventSink = { event ->
        when (event) {
          HomeUiEvent.RefreshRecentUpdates -> {
            recentUpdatesRefreshCount++
          }

          is HomeUiEvent.GotoDetail -> {
            navigator.goTo(DetailScreen(event.item.id))
          }
        }
      },
    )
  }
}
