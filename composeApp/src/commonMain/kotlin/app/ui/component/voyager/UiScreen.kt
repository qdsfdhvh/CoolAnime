package app.ui.component.voyager

import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.core.screen.Screen

interface UiScreen : Screen {
  val provider: ScreenProvider
  interface Factory {
    fun create(screen: ScreenProvider): UiScreen?
  }
}
