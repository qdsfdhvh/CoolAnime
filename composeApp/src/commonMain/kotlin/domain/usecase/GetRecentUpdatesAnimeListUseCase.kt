package domain.usecase

import app.ui.component.state.UiState
import app.ui.component.state.toUi
import domain.model.AnimeShell
import domain.repo.AnimeRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.tatarka.inject.annotations.Inject

@Inject
class GetRecentUpdatesAnimeListUseCase(
  private val animeRepository: AnimeRepository,
) : FlowUseCase<Unit, UiState<ImmutableList<AnimeShell>>>() {
  override fun createObservable(params: Unit): Flow<UiState<ImmutableList<AnimeShell>>> {
    return flow {
      emit(
        runCatching {
          animeRepository.filterAnimeBy(region = "日本", pageIndex = 0).toImmutableList()
        }.toUi(),
      )
    }
  }
}
