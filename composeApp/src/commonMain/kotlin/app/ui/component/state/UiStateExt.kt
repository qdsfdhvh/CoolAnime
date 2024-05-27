package app.ui.component.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProduceStateScope
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState

fun <T> Result<T>.toUi(): UiState<T> {
  return fold(
    onSuccess = { UiState.Success(it) },
    onFailure = { UiState.Failure(it) },
  )
}

fun <T, R> Result<T>.toUi(mapper: (T) -> R): UiState<R> {
  return fold(
    onSuccess = { UiState.Success(mapper(it)) },
    onFailure = { UiState.Failure(it) },
  )
}

@Composable
fun <T> produceUiState(
  producer: suspend ProduceStateScope<UiState<T>>.() -> Unit,
): State<UiState<T>> {
  return produceState(UiState.Loading, producer)
}

@Composable
fun <T> produceUiState(
  key1: Any?,
  producer: suspend ProduceStateScope<UiState<T>>.() -> Unit,
): State<UiState<T>> {
  return produceState(UiState.Loading, key1, producer)
}
