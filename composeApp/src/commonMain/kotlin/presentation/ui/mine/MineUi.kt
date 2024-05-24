package presentation.ui.mine

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seiko.anime.compiler.annotations.BindUi
import presentation.route.MineScreen

@BindUi(MineScreen::class, MineUiState::class)
@Composable
fun MineUi(
  state: MineUiState,
  modifier: Modifier,
) {
  val eventSink = state.eventSink
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
  ) {
    Text("Mine")
  }
}