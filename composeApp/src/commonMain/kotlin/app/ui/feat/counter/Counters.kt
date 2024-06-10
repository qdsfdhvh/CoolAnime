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
import app.ui.component.voyager.ProviderNavigator
import app.ui.component.voyager.VoyagerUiEvent
import app.ui.component.voyager.VoyagerUiState
import com.seiko.anime.compiler.annotations.BindPresenter
import com.seiko.anime.compiler.annotations.BindUi
import domain.repo.AnimeRepository
import me.tatarka.inject.annotations.Assisted
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
@Composable
fun CounterPresenter(
  @Assisted screen: CounterScreen,
  @Assisted navigator: ProviderNavigator,
  animeRepository: Lazy<AnimeRepository>,
): CounterUiState {
  var count by remember { mutableIntStateOf(screen.count) }
  return CounterUiState(
    count = count,
    eventSink = { event ->
      when (event) {
        CounterUiEvent.Add -> count++
        CounterUiEvent.Del -> count--
        CounterUiEvent.GotoDetail -> {
          navigator.push(DetailScreen(Random.nextLong()))
        }
      }
    },
  )
}

data class CounterUiState(
  val count: Int,
  val eventSink: (CounterUiEvent) -> Unit,
) : VoyagerUiState

sealed interface CounterUiEvent : VoyagerUiEvent {
  data object Add : CounterUiEvent
  data object Del : CounterUiEvent
  data object GotoDetail : CounterUiEvent
}

//
// ⬇️ generate codes
//

// @Inject
// class CountUiPresenterFactory(
//   private val presenterFactory: (CounterScreen, ProviderNavigator) -> CountUiPresenter,
// ) : UiPresenter.Factory {
//   override fun create(screen: ScreenProvider, navigator: ProviderNavigator): UiPresenter<*>? {
//     return when (screen) {
//       is CounterScreen -> {
//         presenterFactory(screen, navigator)
//       }
//       else -> null
//     }
//   }
// }
//
// @Inject
// class CountUiPresenter(
//   @Assisted private val screen: CounterScreen,
//   @Assisted private val navigator: ProviderNavigator,
//   private val animeRepository: Lazy<AnimeRepository>,
// ) : MoleculeUiPresenter<CounterUiState>() {
//   @Composable
//   override fun present(): CounterUiState {
//     return CounterPresenter(
//       screen = screen,
//       navigator = navigator,
//       animeRepository = animeRepository,
//     )
//   }
// }
//
// @Inject
// class CounterUiScreenFactory : UiScreen.Factory {
//   override fun create(screen: ScreenProvider): UiScreen? {
//     return when (screen) {
//       is CounterScreen -> {
//         CounterUiScreen(screen)
//       }
//       else -> null
//     }
//   }
// }
//
// data class CounterUiScreen(
//   override val provider: CounterScreen
// ) : MoleculeUiScreen<CounterUiState>() {
//   @Composable
//   override fun Content(state: CounterUiState, navigator: Navigator) {
//     CounterUi(
//       state = state,
//       modifier = Modifier,
//     )
//   }
// }
//
// interface CounterComponent {
//
//   @IntoSet
//   @Provides
//   @ActivityScope
//   fun provideCounterUiPresenterFactory(
//     factory: CountUiPresenterFactory,
//   ): UiPresenter.Factory = factory
//
//   @IntoSet
//   @Provides
//   @ActivityScope
//   fun provideCounterUiScreenFactory(
//     factory: CounterUiScreenFactory,
//   ): UiScreen.Factory = factory
// }
