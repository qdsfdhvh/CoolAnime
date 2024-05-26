package app.ui.component.navigation.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableTarget
import androidx.compose.runtime.Stable
import app.ui.component.navigation.runtime.BaseUiState
import app.ui.component.navigation.runtime.Navigator
import app.ui.component.navigation.screen.Screen

@Stable
interface Presenter<UiState : BaseUiState> {

  @Composable
  @ComposableTarget("presenter")
  fun present(): UiState

  @Stable
  interface Factory {
    fun create(screen: Screen, navigator: Navigator): Presenter<*>?
  }
}

inline fun <UiState : BaseUiState> presenterOf(
  crossinline body: @Composable () -> UiState,
): Presenter<UiState> {
  return object : Presenter<UiState> {
    @Composable
    override fun present(): UiState {
      return body()
    }
  }
}
