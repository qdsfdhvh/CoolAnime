package presentation.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seiko.anime.compiler.annotations.BindUi
import presentation.route.HomeScreen

@BindUi(HomeScreen::class, HomeUiState::class)
@Composable
fun HomeUi(
  state: HomeUiState,
  modifier: Modifier,
) {
  val eventSink = state.eventSink
  Scaffold(
    modifier = modifier,
  ) { innerPadding ->
    Column(
      modifier = Modifier.padding(innerPadding).fillMaxSize(),
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
      Spacer(Modifier.height(32.dp))
      Button(onClick = { eventSink(HomeUiEvent.GotoDetail) }) {
        Text("跳转详情")
      }
    }
  }
}
