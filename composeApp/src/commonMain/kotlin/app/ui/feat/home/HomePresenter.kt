package app.ui.feat.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import app.route.CounterScreen
import app.route.DetailScreen
import app.route.HomeScreen
import app.ui.component.state.produceUiState
import app.ui.component.state.toUi
import app.ui.component.voyager.ProviderNavigator
import com.seiko.anime.compiler.annotations.BindPresenter
import domain.usecase.GetRecentUpdatesAnimeListUseCase
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import kotlin.random.Random

@BindPresenter(HomeScreen::class)
@Inject
@Composable
fun HomePresenter(
  @Assisted navigator: ProviderNavigator,
  getRecentUpdatesAnimeListUseCase: Lazy<GetRecentUpdatesAnimeListUseCase>,
): HomeUiState {
  var recentUpdatesStateRefreshKey by remember { mutableIntStateOf(0) }
  val recentUpdatesState by produceUiState(recentUpdatesStateRefreshKey) {
    value = getRecentUpdatesAnimeListUseCase.value.invoke().toUi()
  }
  return HomeUiState(
    recentUpdatesState = recentUpdatesState,
    eventSink = { event ->
      when (event) {
        HomeUiEvent.RefreshRecentUpdates -> {
          recentUpdatesStateRefreshKey++
        }

        is HomeUiEvent.GotoDetail -> {
          navigator.push(DetailScreen(event.item.id))
        }

        HomeUiEvent.GotoCounter -> {
          navigator.push(CounterScreen(Random.nextInt()))
        }
      }
    },
  )
}
