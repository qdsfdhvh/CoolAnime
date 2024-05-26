package app.ui.feat.mine

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.route.MineScreen
import com.seiko.anime.compiler.annotations.CircuitInject

@CircuitInject(MineScreen::class)
@Composable
fun MineUi(
  state: MineUiState,
  modifier: Modifier,
) {
  val eventSink = state.eventSink
  Column(
    modifier = modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
  ) {
    Text("Mine")
  }
}
