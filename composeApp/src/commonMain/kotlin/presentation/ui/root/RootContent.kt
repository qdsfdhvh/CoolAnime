package presentation.ui.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.slack.circuit.backstack.SaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
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
  @Composable
  override fun Content(
    backStack: SaveableBackStack,
    navigator: Navigator,
    modifier: Modifier,
  ) {
    CircuitCompositionLocals(circuit) {
      MaterialTheme {
        NavigableCircuitContent(
          backStack = backStack,
          navigator = navigator,
          modifier = Modifier.fillMaxSize(),
        )
      }
    }
  }
}
