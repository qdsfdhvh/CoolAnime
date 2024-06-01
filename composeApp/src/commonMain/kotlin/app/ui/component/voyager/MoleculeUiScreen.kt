package app.ui.component.voyager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow

abstract class MoleculeUiScreen<T : VoyagerUiState> : UiScreen {
  @Suppress("UNCHECKED_CAST")
  @Composable
  override fun Content() {
    val navigator = LocalNavigator.currentOrThrow
    val repository = LocalUiScreenRegistry.current
    val state by rememberScreenModel {
      repository.createPresenter(provider, navigator) as MoleculeUiPresenter<T>
    }.state
    state?.let {
      Content(it, navigator)
    }
  }

  @Composable
  abstract fun Content(state: T, navigator: Navigator)
}
