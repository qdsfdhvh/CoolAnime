package presentation.inject

import com.slack.circuit.foundation.Circuit
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import me.tatarka.inject.annotations.Provides
import presentation.ui.home.HomeUiComponent
import presentation.ui.root.DefaultRootContent
import presentation.ui.root.RootContent

interface UiComponent : HomeUiComponent {

  @ActivityScope
  @Provides
  fun bindRootContent(impl: DefaultRootContent): RootContent = impl

  @Provides
  @ActivityScope
  fun provideCircuit(
    uiFactories: Set<Ui.Factory>,
    presenterFactories: Set<Presenter.Factory>,
  ): Circuit = Circuit.Builder()
    .addUiFactories(uiFactories)
    .addPresenterFactories(presenterFactories)
    .build()
}
