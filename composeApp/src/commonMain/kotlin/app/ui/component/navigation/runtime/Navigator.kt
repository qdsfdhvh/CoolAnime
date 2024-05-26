package app.ui.component.navigation.runtime

import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import app.ui.component.navigation.screen.Screen
import com.benasher44.uuid.uuid4

interface Navigator : ViewModelStoreOwner, BackStack<Screen> {
  val rootNavigator: Navigator
  val backStacks: BackStack<BackStackEntry>
}

private class NavigatorImpl(
  initialScreen: Screen,
  private val parent: Navigator? = null,
  private val key: String,
) : Navigator {

  override val viewModelStore: ViewModelStore by lazy {
    ViewModelStore()
  }

  override val rootNavigator: Navigator
    get() = parent?.rootNavigator ?: this

  override val backStacks = mutableBackStackOf(initialScreen.toEntry())

  private var id: Int = 0

  override val size: Int
    get() = backStacks.size

  override val canGoBack: Boolean
    get() = backStacks.canGoBack

  override fun push(item: Screen): Boolean {
    return backStacks.push(item.toEntry())
  }

  override fun pop(): Screen? {
    val popEntry = backStacks.pop()
    popEntry?.destroy()
    return popEntry?.screen
  }

  override fun popUntil(predicate: (Screen) -> Boolean) {
    return backStacks.popUntil {
      val pop = predicate(it.screen)
      if (pop) {
        it.destroy()
      }
      pop
    }
  }

  override fun replace(item: Screen): Screen? {
    val replacedEntry = backStacks.replace(item.toEntry())
    replacedEntry?.destroy()
    return replacedEntry?.screen
  }

  override fun replaceAll(item: Screen) {
    backStacks.forEach { it.destroy() }
    backStacks.replaceAll(item.toEntry())
  }

  override fun replaceAll(items: Collection<Screen>) {
    backStacks.forEach { it.destroy() }
    backStacks.replaceAll(
      items.map { it.toEntry() },
    )
  }

  override fun iterator(): Iterator<Screen> {
    val iterator = backStacks.iterator()
    return object : Iterator<Screen> {
      override fun hasNext(): Boolean = iterator.hasNext()
      override fun next(): Screen = iterator.next().screen
    }
  }

  private fun Screen.toEntry() = BackStackEntry(
    key = "$key-${id++}",
    screen = this,
  )
}

fun Navigator(initialScreen: Screen, parent: Navigator? = null, key: String = uuid4().toString()): Navigator {
  return NavigatorImpl(initialScreen, parent, key)
}
