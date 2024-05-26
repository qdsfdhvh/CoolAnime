package presentation.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seiko.anime.compiler.annotations.CircuitInject
import presentation.route.DetailScreen

@CircuitInject(DetailScreen::class)
@Composable
fun DetailUi(
  state: DetailUiState,
  modifier: Modifier,
) {
  val eventSink = state.eventSink
  Column(
    modifier = modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
  ) {
    Text("id: ${state.id}")
    Button(onClick = { eventSink(DetailUiEvent.Request) }) {
      Text("请求")
    }
    Text(state.content)
  }
}
