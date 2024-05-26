package presentation.ui.counter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seiko.anime.compiler.annotations.CircuitInject
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import presentation.route.CounterScreen
import presentation.route.DetailScreen
import kotlin.random.Random

@CircuitInject(CounterScreen::class)
@Composable
fun CounterUi(state: CounterUiState, modifier: Modifier) {
  val eventSink = state.eventSink
  Column(
    modifier = modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
  ) {
    Button(onClick = { eventSink(CounterUiEvent.Add) }) {
      Text("+")
    }
    Text(text = state.count.toString())
    Button(onClick = { eventSink(CounterUiEvent.Add) }) {
      Text("-")
    }
    Button(onClick = { eventSink(CounterUiEvent.GotoDetail) }) {
      Text("Goto Detail")
    }
  }
}

@CircuitInject(CounterScreen::class)
@Inject
class CounterPresenter(
  @Assisted private val navigator: Navigator,
) : Presenter<CounterUiState> {
  @Composable
  override fun present(): CounterUiState {
    var count by rememberRetained { mutableIntStateOf(0) }
    return CounterUiState(
      count = count,
      eventSink = { event ->
        when (event) {
          CounterUiEvent.Add -> count++
          CounterUiEvent.Del -> count--
          CounterUiEvent.GotoDetail -> {
            navigator.goTo(DetailScreen(Random.nextInt()))
          }
        }
      },
    )
  }
}

data class CounterUiState(
  val count: Int,
  val eventSink: (CounterUiEvent) -> Unit,
) : CircuitUiState

sealed interface CounterUiEvent : CircuitUiEvent {
  data object Add : CounterUiEvent
  data object Del : CounterUiEvent
  data object GotoDetail : CounterUiEvent
}
