package app.ui.component.navigation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import app.ui.component.navigation.runtime.BaseUiState
import app.ui.component.navigation.screen.Screen

@Stable
interface Ui<UiState : BaseUiState> {

  @Composable
  fun Content(state: UiState, modifier: Modifier)

  @Stable
  interface Factory {
    fun create(screen: Screen): Ui<*>?
  }
}

inline fun <UiState : BaseUiState> ui(
  crossinline body: @Composable (state: UiState, modifier: Modifier) -> Unit,
): Ui<UiState> {
  return object : Ui<UiState> {
    @Composable
    override fun Content(state: UiState, modifier: Modifier) {
      body(state, modifier)
    }
  }
}
