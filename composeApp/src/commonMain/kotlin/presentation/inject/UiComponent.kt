package presentation.inject

import com.slack.circuit.foundation.Circuit
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import me.tatarka.inject.annotations.Provides
import presentation.ui.root.DefaultRootContent
import presentation.ui.root.RootContent
import presentation.util.logging.AppLogger

interface UiComponent : BindUiComponent, BindPresenterComponent {

  @ActivityScope
  @Provides
  fun bindRootContent(impl: DefaultRootContent): RootContent = impl

  @ActivityScope
  @Provides
  fun provideCircuit(
    uiFactories: Set<Ui.Factory>,
    presenterFactories: Set<Presenter.Factory>,
    logger: AppLogger,
  ): Circuit = Circuit.Builder()
    .addUiFactories(uiFactories)
    .addPresenterFactories(presenterFactories)
    .build()
}
