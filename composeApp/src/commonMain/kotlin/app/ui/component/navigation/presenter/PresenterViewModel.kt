package app.ui.component.navigation.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.molecule.RecompositionMode
import app.cash.molecule.launchMolecule
import app.ui.component.navigation.runtime.Navigation
import app.ui.component.navigation.runtime.Navigator
import app.ui.component.navigation.screen.Screen

internal class PresenterViewModel(
  screen: Screen,
  navigator: Navigator,
  factoryManager: Navigation,
) : ViewModel() {
  private val presenter by lazy {
    factoryManager.createPresenter(screen, navigator)
  }
  val bodyFlow = viewModelScope.launchMolecule(RecompositionMode.Immediate) {
    presenter?.present()
  }
}
