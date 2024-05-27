package app.ui.component.navigation.runtime

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import app.ui.component.navigation.screen.Screen
import com.benasher44.uuid.uuid4

@Stable
interface Navigator : ViewModelStoreOwner, BackStack<Screen>

@Stable
private class NavigatorImpl(
  private val key: String,
  private val backStacks: BackStack<BackStackEntry>,
) : Navigator {

  override val viewModelStore: ViewModelStore by lazy {
    ViewModelStore()
  }

  override val size: Int
    get() = backStacks.size

  override val canPop: Boolean
    get() = backStacks.canPop

  override fun push(item: Screen): Boolean {
    return backStacks.push(item.toEntry())
  }

  override fun pop(): Screen? {
    return backStacks.pop()?.screen
  }

  override fun popUntil(inclusive: Boolean, predicate: (Screen) -> Boolean) {
    return backStacks.popUntil(inclusive) { predicate(it.screen) }
  }

  fun popUntil(inclusive: Boolean = false, predicate: (String, Screen) -> Boolean) {
    return backStacks.popUntil(inclusive) { predicate(it.key, it.screen) }
  }

  override fun replace(item: Screen): Screen? {
    return backStacks.replace(item.toEntry())?.screen
  }

  override fun replaceAll(item: Screen) {
    backStacks.replaceAll(item.toEntry())
  }

  override fun replaceAll(items: Collection<Screen>) {
    backStacks.replaceAll(items.map { it.toEntry() })
  }

  override fun iterator(): Iterator<Screen> {
    val iterator = backStacks.iterator()
    return object : Iterator<Screen> {
      override fun hasNext(): Boolean = iterator.hasNext()
      override fun next(): Screen = iterator.next().screen
    }
  }

  private var id = 0
  private fun Screen.toEntry() = BackStackEntry(
    key = "$key-${id++}",
    screen = this,
  )
}

fun Navigator(
  backStacks: BackStack<BackStackEntry>,
  key: String = uuid4().toString(),
): Navigator {
  return NavigatorImpl(key, backStacks)
}
