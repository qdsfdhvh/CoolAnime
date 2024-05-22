package presentation.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import presentation.route.HomeScreen

@Inject
class HomePresenterFactory(
  private val presenterFactory: (Navigator) -> HomePresenter,
) : Presenter.Factory {
  override fun create(
    screen: Screen,
    navigator: Navigator,
    context: CircuitContext,
  ): Presenter<*>? {
    return when (screen) {
      is HomeScreen -> presenterFactory(navigator)
      else -> null
    }
  }
}

@Inject
class HomePresenter(
  @Assisted private val navigator: Navigator,
) : Presenter<HomeUiState> {
  @Composable
  override fun present(): HomeUiState {
    var count by remember { mutableStateOf(0) }
    return HomeUiState(
      count = count,
      eventSink = { event ->
        when (event) {
          HomeUiEvent.Add -> count++
          HomeUiEvent.Del -> count--
        }
      },
    )
  }
}
