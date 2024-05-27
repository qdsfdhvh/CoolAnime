package app.ui.component.navigation.runtime

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import app.ui.component.navigation.screen.Screen

@Stable
interface SaveableBackStack : BackStack<BackStackEntry>

@Stable
private class SaveableBackStackImpl(initEntries: List<BackStackEntry>) : SaveableBackStack {

  private val backStack = mutableBackStackOf(initEntries)

  override val size: Int
    get() = backStack.size

  override val canPop: Boolean
    get() = backStack.canPop

  override fun pop(): BackStackEntry? {
    return backStack.pop()?.apply {
      destroy()
    }
  }

  override fun replaceAll(items: Collection<BackStackEntry>) {
    backStack.forEach { it.destroy() }
    backStack.replaceAll(items)
  }

  override fun replaceAll(item: BackStackEntry) {
    backStack.forEach { it.destroy() }
    backStack.replaceAll(item)
  }

  override fun replace(item: BackStackEntry): BackStackEntry? {
    return backStack.replace(item)?.apply {
      destroy()
    }
  }

  override fun popUntil(inclusive: Boolean, predicate: (BackStackEntry) -> Boolean) {
    backStack.popUntil(inclusive) {
      val isTarget = predicate(it)
      if (!isTarget || inclusive) {
        it.destroy()
      }
      isTarget
    }
  }

  override fun push(item: BackStackEntry): Boolean {
    return backStack.push(item)
  }

  override fun iterator(): Iterator<BackStackEntry> {
    return backStack.iterator()
  }

  companion object {
    private var id = 0
    private fun Screen.toEntry() = BackStackEntry(
      key = "init-${id++}",
      screen = this,
    )

    fun from(initScreens: List<Screen>) = SaveableBackStackImpl(
      initScreens.map { it.toEntry() },
    )

    @Suppress("UNCHECKED_CAST")
    var saver = listSaver<SaveableBackStack, Any>(
      save = { backStack ->
        backStack.toList()
      },
      restore = {
        SaveableBackStackImpl(it as List<BackStackEntry>)
      },
    )
  }
}

@Composable
fun rememberSaveableBackStack(initScreen: Screen): SaveableBackStack {
  return rememberSaveable(saver = SaveableBackStackImpl.saver) {
    SaveableBackStackImpl.from(listOf(initScreen))
  }
}
