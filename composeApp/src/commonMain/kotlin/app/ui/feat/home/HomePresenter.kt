package app.ui.feat.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.route.CounterScreen
import app.route.DetailScreen
import app.route.HomeScreen
import app.ui.component.navigation.runtime.Navigator
import app.ui.component.state.UiState
import com.seiko.anime.compiler.annotations.BindPresenter
import domain.usecase.GetRecentUpdatesAnimeListUseCase
import domain.usecase.invoke
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@BindPresenter(HomeScreen::class)
@Inject
@Composable
fun HomePresenter(
  @Assisted navigator: Navigator,
  getRecentUpdatesAnimeListUseCase: Lazy<GetRecentUpdatesAnimeListUseCase>,
): HomeUiState {
  val recentUpdatesState by getRecentUpdatesAnimeListUseCase.value.flow
    .collectAsState(UiState.Loading)
  LaunchedEffect(Unit) {
    getRecentUpdatesAnimeListUseCase.value.invoke()
  }
  return HomeUiState(
    recentUpdatesState = recentUpdatesState,
    eventSink = { event ->
      when (event) {
        HomeUiEvent.RefreshRecentUpdates -> {
          getRecentUpdatesAnimeListUseCase.value.invoke()
        }

        is HomeUiEvent.GotoDetail -> {
          navigator.push(DetailScreen(event.item.id))
        }

        HomeUiEvent.GotoCounter -> {
          navigator.push(CounterScreen)
        }
      }
    },
  )
}
