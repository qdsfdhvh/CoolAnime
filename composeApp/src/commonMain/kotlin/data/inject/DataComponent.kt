package data.inject

import app.inject.ApplicationScope
import data.repo.AnimeRepositoryImpl
import domain.repo.AnimeRepository
import me.tatarka.inject.annotations.Provides

interface DataComponent : DataPlatformComponent {
  @ApplicationScope
  @Provides
  fun provideAnimeRepository(impl: AnimeRepositoryImpl): AnimeRepository = impl
}
