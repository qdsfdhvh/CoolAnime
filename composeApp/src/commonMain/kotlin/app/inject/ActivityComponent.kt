package app.inject

import app.ui.component.voyager.UiPresenter
import app.ui.component.voyager.UiScreen
import app.ui.component.voyager.UiScreenRegistry
import app.ui.feat.root.DefaultRootContent
import app.ui.feat.root.RootContent
import me.tatarka.inject.annotations.Provides

interface ActivityComponent : BindUiComponent, BindPresenterComponent {

  @ActivityScope
  @Provides
  fun bindRootContent(impl: DefaultRootContent): RootContent = impl

  @ActivityScope
  @Provides
  fun provideUiScreenRegistry(
    uiFactories: Set<UiScreen.Factory>,
    presenterFactories: Set<UiPresenter.Factory>,
  ): UiScreenRegistry {
    return UiScreenRegistry.Builder()
      .addUiFactories(uiFactories)
      .addPresenterFactories(presenterFactories)
      .build()
  }
}
