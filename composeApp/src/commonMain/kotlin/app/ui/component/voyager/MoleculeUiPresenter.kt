package app.ui.component.voyager

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import app.cash.molecule.RecompositionMode
import app.cash.molecule.launchMolecule
import cafe.adriel.voyager.core.model.screenModelScope

abstract class MoleculeUiPresenter<T : VoyagerUiState> : UiPresenter<T> {
  val state: State<T?> by lazy {
    mutableStateOf<T?>(null).apply {
      screenModelScope.launchMolecule(
        mode = RecompositionMode.Immediate,
        emitter = { value = it },
        body = { present() },
      )
    }
  }
}
