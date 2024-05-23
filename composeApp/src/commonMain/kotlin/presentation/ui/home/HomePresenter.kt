package presentation.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.seiko.anime.compiler.annotations.BindPresenter
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import presentation.route.DetailScreen
import presentation.route.HomeScreen
import kotlin.random.Random

@BindPresenter(HomeScreen::class)
@Inject
class HomePresenter(
  @Assisted private val navigator: Navigator,
) : Presenter<HomeUiState> {
  @Composable
  override fun present(): HomeUiState {
    var count by remember { mutableIntStateOf(0) }
    return HomeUiState(
      count = count,
      eventSink = { event ->
        when (event) {
          HomeUiEvent.Add -> count++
          HomeUiEvent.Del -> count--
          HomeUiEvent.GotoDetail -> {
            navigator.goTo(DetailScreen(Random.nextLong()))
          }
        }
      },
    )
  }
}
