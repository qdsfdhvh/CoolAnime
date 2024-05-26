package presentation.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import com.seiko.anime.compiler.annotations.CircuitInject
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import kotlinx.coroutines.delay
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import presentation.component.state.UiState
import presentation.route.CounterScreen
import presentation.route.DetailScreen
import presentation.route.HomeScreen
import presentation.usecase.GetRecentUpdatesAnimeListUseCase
import presentation.usecase.invoke

@CircuitInject(HomeScreen::class)
@Inject
class HomePresenter(
  @Assisted private val navigator: Navigator,
  private val getRecentUpdatesAnimeListUseCase: Lazy<GetRecentUpdatesAnimeListUseCase>,
) : Presenter<HomeUiState> {
  @Composable
  override fun present(): HomeUiState {
    val recentUpdatesState by getRecentUpdatesAnimeListUseCase.value.flow.collectAsRetainedState(UiState.Loading)
    LaunchedEffect(Unit) {
      delay(20)
      if (recentUpdatesState is UiState.Loading) {
        getRecentUpdatesAnimeListUseCase.value.invoke()
      }
    }
    return HomeUiState(
      recentUpdatesState = recentUpdatesState,
      eventSink = { event ->
        when (event) {
          HomeUiEvent.RefreshRecentUpdates -> {
            getRecentUpdatesAnimeListUseCase.value.invoke()
          }

          is HomeUiEvent.GotoDetail -> {
            navigator.goTo(DetailScreen(event.item.id))
          }

          HomeUiEvent.GotoCounter -> {
            navigator.goTo(CounterScreen)
          }
        }
      },
    )
  }
}
