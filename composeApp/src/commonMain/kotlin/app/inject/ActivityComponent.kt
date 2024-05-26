package app.inject

import app.ui.component.navigation.presenter.Presenter
import app.ui.component.navigation.runtime.Navigation
import app.ui.component.navigation.ui.Ui
import app.ui.feat.root.DefaultRootContent
import app.ui.feat.root.RootContent
import me.tatarka.inject.annotations.Provides

interface ActivityComponent : BindUiComponent, BindPresenterComponent {

  @ActivityScope
  @Provides
  fun bindRootContent(impl: DefaultRootContent): RootContent = impl

  @ActivityScope
  @Provides
  fun provideNavigation(
    uiFactories: Set<Ui.Factory>,
    presenterFactories: Set<Presenter.Factory>,
  ): Navigation {
    return Navigation.Builder()
      .addUiFactories(uiFactories)
      .addPresenterFactories(presenterFactories)
      .build()
  }
}
