package app.ui.component.navigation.runtime

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import app.ui.component.navigation.presenter.Presenter
import app.ui.component.navigation.screen.Screen
import app.ui.component.navigation.ui.Ui

@Immutable
class Navigation private constructor(
  private val uiFactories: Set<Ui.Factory>,
  private val presenterFactories: Set<Presenter.Factory>,
) {

  fun crateUi(screen: Screen): Ui<BaseUiState>? {
    @Suppress("UNCHECKED_CAST")
    return uiFactories.asSequence()
      .mapNotNull { it.create(screen) }
      .firstOrNull() as? Ui<BaseUiState>
  }

  fun createPresenter(screen: Screen, navigator: Navigator): Presenter<BaseUiState>? {
    @Suppress("UNCHECKED_CAST")
    return presenterFactories.asSequence()
      .mapNotNull { it.create(screen, navigator) }
      .firstOrNull() as? Presenter<BaseUiState>
  }

  class Builder {
    private val uiFactories = mutableSetOf<Ui.Factory>()
    private val presenterFactories = mutableSetOf<Presenter.Factory>()

    fun addUiFactories(uiFactories: Set<Ui.Factory>) = apply {
      this.uiFactories.addAll(uiFactories)
    }

    fun addPresenterFactories(presenterFactories: Set<Presenter.Factory>) = apply {
      this.presenterFactories.addAll(presenterFactories)
    }

    fun build() = Navigation(
      uiFactories = uiFactories.toSet(),
      presenterFactories = presenterFactories.toSet(),
    )
  }
}

val LocalNavigation: ProvidableCompositionLocal<Navigation> =
  staticCompositionLocalOf {
    error("No NavigatorFactoryManager provided")
  }
