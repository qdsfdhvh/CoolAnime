package presentation.ui.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.slack.circuit.backstack.SaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.runtime.Navigator
import me.tatarka.inject.annotations.Inject

interface RootContent {
  @Composable
  fun Content(
    backStack: SaveableBackStack,
    navigator: Navigator,
    modifier: Modifier,
  )
}

@Inject
class DefaultRootContent(
  private val circuit: Circuit,
) : RootContent {
  @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
  @Composable
  override fun Content(
    backStack: SaveableBackStack,
    navigator: Navigator,
    modifier: Modifier,
  ) {
    val windowSizeClass = calculateWindowSizeClass()

    CircuitCompositionLocals(circuit) {
      MaterialTheme {
        RootUi(
          backStack = backStack,
          navigator = navigator,
          windowSizeClass = windowSizeClass,
          modifier = modifier.fillMaxSize(),
        )
      }
    }
  }
}
