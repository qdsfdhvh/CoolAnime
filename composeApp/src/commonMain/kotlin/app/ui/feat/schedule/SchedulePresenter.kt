package app.ui.feat.schedule

import androidx.compose.runtime.Composable
import app.route.ScheduleScreen
import app.ui.component.navigation.runtime.Navigator
import com.seiko.anime.compiler.annotations.BindPresenter
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@BindPresenter(ScheduleScreen::class)
@Inject
@Composable
fun SchedulePresenter(
  @Assisted navigator: Navigator,
): ScheduleUiState {
  return ScheduleUiState(
    eventSink = { event ->
    },
  )
}
