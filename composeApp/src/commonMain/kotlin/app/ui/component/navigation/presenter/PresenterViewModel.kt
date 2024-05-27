package app.ui.component.navigation.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import app.ui.component.navigation.runtime.Navigation
import app.ui.component.navigation.runtime.Navigator
import app.ui.component.navigation.screen.Screen
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

internal class PresenterViewModel(
  screen: Screen,
  navigator: Navigator,
  factoryManager: Navigation,
) : ViewModel() {
  private val presenter by lazy {
    factoryManager.createPresenter(screen, navigator)
  }
  val bodyFlow = moleculeFlow(RecompositionMode.Immediate) {
    presenter?.present()
  }.stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(5_000),
    initialValue = null,
  )
}
