package app.ui.component.state

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
