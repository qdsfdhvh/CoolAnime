package domain.usecase

import app.util.AppCoroutineDispatchers
import domain.model.AnimeShell
import domain.repo.AnimeRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.withContext
import me.tatarka.inject.annotations.Inject

@Inject
class GetWeeklyScheduleUseCase(
  private val appCoroutineDispatchers: AppCoroutineDispatchers,
  private val animeRepository: AnimeRepository,
) {
  suspend operator fun invoke(): Result<ImmutableList<ImmutableList<AnimeShell>>> {
    return withContext(appCoroutineDispatchers.io) {
      runCatching {
        animeRepository.getWeeklySchedule()
          .map { it.toImmutableList() }
          .toImmutableList()
      }
    }
  }
}
