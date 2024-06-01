package app.ui.component.voyager

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import app.cash.molecule.RecompositionMode
import app.cash.molecule.launchMolecule
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope

abstract class MoleculeUiPresenter<T : VoyagerUiState> : ScreenModel, UiPresenter<T> {
  val state: State<T?> by lazy {
    val state = mutableStateOf<T?>(null)
    screenModelScope.launchMolecule(
      mode = RecompositionMode.Immediate,
      emitter = { state.value = it },
      body = { present() },
    )
    state
  }
}
