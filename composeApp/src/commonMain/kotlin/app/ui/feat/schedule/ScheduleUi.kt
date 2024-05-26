package app.ui.feat.schedule

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.route.ScheduleScreen
import com.seiko.anime.compiler.annotations.BindUi

@BindUi(ScheduleScreen::class)
@Composable
fun ScheduleUi(
  state: ScheduleUiState,
  modifier: Modifier,
) {
  val eventSink = state.eventSink
  Column(
    modifier = modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
  ) {
    Text("Schedule")
  }
}
