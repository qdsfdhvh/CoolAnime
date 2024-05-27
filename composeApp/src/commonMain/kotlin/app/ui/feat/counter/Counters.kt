package app.ui.feat.counter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.route.CounterScreen
import app.route.DetailScreen
import app.ui.component.navigation.runtime.BaseUiEvent
import app.ui.component.navigation.runtime.BaseUiState
import app.ui.component.navigation.runtime.Navigator
import app.ui.component.navigation.ui.ui
import com.seiko.anime.compiler.annotations.BindPresenter
import com.seiko.anime.compiler.annotations.BindUi
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import kotlin.random.Random

@BindUi(CounterScreen::class)
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

@BindPresenter(CounterScreen::class)
@Inject
@Composable
fun CounterPresenter(
  @Assisted navigator: Navigator,
): CounterUiState {
  var count by remember { mutableIntStateOf(0) }
  return CounterUiState(
    count = count,
    eventSink = { event ->
      when (event) {
        CounterUiEvent.Add -> count++
        CounterUiEvent.Del -> count--
        CounterUiEvent.GotoDetail -> {
          navigator.push(DetailScreen(Random.nextInt()))
        }
      }
    },
  )
}

data class CounterUiState(
  val count: Int,
  val eventSink: (CounterUiEvent) -> Unit,
) : BaseUiState

sealed interface CounterUiEvent : BaseUiEvent {
  data object Add : CounterUiEvent
  data object Del : CounterUiEvent
  data object GotoDetail : CounterUiEvent
}
