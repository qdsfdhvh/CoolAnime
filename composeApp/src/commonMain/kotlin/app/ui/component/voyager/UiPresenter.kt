package app.ui.component.voyager

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.registry.ScreenProvider

interface UiPresenter<T : VoyagerUiState> : ScreenModel {

  @Composable
  fun present(): T

  interface Factory {
    fun create(screen: ScreenProvider, navigator: ProviderNavigator): UiPresenter<*>?
  }
}
