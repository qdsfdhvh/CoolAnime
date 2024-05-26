package app.ui.feat.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import app.route.CounterScreen
import app.route.DetailScreen
import app.route.HomeScreen
import app.ui.component.state.UiState
import com.seiko.anime.compiler.annotations.CircuitInject
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import domain.usecase.GetRecentUpdatesAnimeListUseCase
import domain.usecase.invoke
import kotlinx.coroutines.delay
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@CircuitInject(HomeScreen::class)
@Inject
class HomePresenter(
  @Assisted private val navigator: Navigator,
  private val getRecentUpdatesAnimeListUseCase: Lazy<GetRecentUpdatesAnimeListUseCase>,
) : Presenter<HomeUiState> {
  @Composable
  override fun present(): HomeUiState {
    val recentUpdatesState by getRecentUpdatesAnimeListUseCase.value.flow.collectAsRetainedState(
      UiState.Loading,
    )
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
