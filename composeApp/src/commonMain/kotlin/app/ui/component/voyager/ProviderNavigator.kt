package app.ui.component.voyager

import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.navigator.Navigator

class ProviderNavigator(
  private val screenRegistry: UiScreenRegistry,
  private val navigator: Navigator,
) {

  fun push(provider: ScreenProvider) {
    navigator.push(screenRegistry.createUi(provider))
  }

  fun pop() {
    navigator.pop()
  }

  fun replace(provider: ScreenProvider) {
    navigator.replace(screenRegistry.createUi(provider))
  }

  fun pushSingle(provider: ScreenProvider) {
    val items = navigator.items
    val index = items.indexOfLast { (it as? UiScreen)?.provider == provider }
    if (index >= 0) {
      val newItems = items.toMutableList().apply {
        add(removeAt(index))
      }
      navigator.replaceAll(newItems)
    } else {
      push(provider)
    }
  }
}
