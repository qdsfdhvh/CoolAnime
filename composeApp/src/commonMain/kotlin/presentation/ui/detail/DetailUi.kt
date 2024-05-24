package presentation.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seiko.anime.compiler.annotations.BindUi
import presentation.route.DetailScreen
import presentation.widget.BackButton

@OptIn(ExperimentalMaterial3Api::class)
@BindUi(DetailScreen::class, DetailUiState::class)
@Composable
fun DetailUi(
  state: DetailUiState,
  modifier: Modifier,
) {
  val eventSink = state.eventSink
  Scaffold(
    modifier = modifier,
    topBar = {
      TopAppBar(
        title = {},
        navigationIcon = {
          BackButton {
            eventSink(DetailUiEvent.Back)
          }
        },
      )
    },
  ) { innerPadding ->
    Column(
      modifier = Modifier.padding(innerPadding).fillMaxSize(),
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
}
