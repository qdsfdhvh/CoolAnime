package app.inject

import app.ui.feat.root.DefaultRootContent
import app.ui.feat.root.RootContent
import app.util.logging.AppLogger
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import me.tatarka.inject.annotations.Provides

interface ActivityComponent : BindUiComponent, BindPresenterComponent {

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
