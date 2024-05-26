package app.ui.component.state

import androidx.compose.runtime.Immutable

@Immutable
sealed interface UiState<out T> {
  val getOrNull: T? get() = null

  data object Loading : UiState<Nothing>

  data class Success<T>(val data: T) : UiState<T> {
    override val getOrNull: T
      get() = data
  }

  data class Failure(val error: Throwable) : UiState<Nothing>
}
