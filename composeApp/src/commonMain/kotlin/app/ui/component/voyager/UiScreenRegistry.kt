package app.ui.component.voyager

import androidx.compose.runtime.staticCompositionLocalOf
import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.navigator.Navigator

class UiScreenRegistry private constructor(
  private val uiFactories: Set<UiScreen.Factory>,
  private val uiPresenterFactories: Set<UiPresenter.Factory>,
) {

  fun createUi(screen: ScreenProvider): UiScreen {
    return uiFactories.firstNotNullOfOrNull { it.create(screen) }
      ?: error("$screen not found UiScreenFactory")
  }

  fun createPresenter(screen: ScreenProvider, navigator: Navigator): UiPresenter<*> {
    val providerNavigator = createProviderNavigator(navigator)
    return uiPresenterFactories.firstNotNullOfOrNull { it.create(screen, providerNavigator) }
      ?: error("$screen not found UiPresenterFactory")
  }

  fun createProviderNavigator(navigator: Navigator): ProviderNavigator {
    return ProviderNavigator(this, navigator)
  }

  class Builder {

    private val uiFactories = mutableSetOf<UiScreen.Factory>()

    private val uiPresenterFactories = mutableSetOf<UiPresenter.Factory>()

    fun addUiFactories(uiFactories: Set<UiScreen.Factory>) = apply {
      this.uiFactories.addAll(uiFactories)
    }

    fun addPresenterFactories(uiPresenterFactories: Set<UiPresenter.Factory>) = apply {
      this.uiPresenterFactories.addAll(uiPresenterFactories)
    }

    fun build(): UiScreenRegistry {
      return UiScreenRegistry(
        uiFactories = uiFactories.toSet(),
        uiPresenterFactories = uiPresenterFactories.toSet(),
      )
    }
  }
}

val LocalUiScreenRegistry = staticCompositionLocalOf<UiScreenRegistry> {
  error("No UiScreenRegistry provided")
}
