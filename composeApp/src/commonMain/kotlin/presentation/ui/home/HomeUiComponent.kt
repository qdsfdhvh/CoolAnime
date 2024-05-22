package presentation.ui.home

import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides
import presentation.inject.ActivityScope

interface HomeUiComponent {

  @IntoSet
  @Provides
  @ActivityScope
  fun bindAccountPresenterFactory(factory: HomePresenterFactory): Presenter.Factory = factory

  @IntoSet
  @Provides
  @ActivityScope
  fun bindAccountUiFactory(factory: AccountUiFactory): Ui.Factory = factory
}
