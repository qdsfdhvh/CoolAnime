package presentation.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import me.tatarka.inject.annotations.Inject
import presentation.route.HomeScreen

@Inject
class AccountUiFactory : Ui.Factory {
  override fun create(screen: Screen, context: CircuitContext): Ui<*>? = when (screen) {
    is HomeScreen -> {
      ui<HomeUiState> { state, modifier ->
        HomeUi(state, modifier)
      }
    }

    else -> null
  }
}

@Composable
fun HomeUi(
  state: HomeUiState,
  modifier: Modifier,
) {
  val eventSink = state.eventSink
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
  ) {
    Button(onClick = { eventSink(HomeUiEvent.Add) }) {
      Text("+")
    }
    Text("count: ${state.count}")
    Button(onClick = { eventSink(HomeUiEvent.Del) }) {
      Text("-")
    }
  }
}
