package domain.usecase

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest

abstract class FlowUseCase<P : Any, T> {

  private val paramState = MutableSharedFlow<P>(
    replay = 1,
    extraBufferCapacity = 1,
    onBufferOverflow = BufferOverflow.DROP_OLDEST,
  )

  operator fun invoke(params: P) {
    paramState.tryEmit(params)
  }

  @OptIn(ExperimentalCoroutinesApi::class)
  val flow: Flow<T> = paramState
    .distinctUntilChanged()
    .flatMapLatest { createObservable(it) }
    .distinctUntilChanged()

  protected abstract fun createObservable(params: P): Flow<T>
}

operator fun <R> FlowUseCase<Unit, R>.invoke() = invoke(Unit)
