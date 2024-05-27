package app.ui.component.navigation.runtime

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.StateFactoryMarker

@Stable
interface BackStack<T> : Iterable<T> {
  val size: Int
  val canPop: Boolean
  fun push(item: T): Boolean
  fun pop(): T?
  fun popUntil(inclusive: Boolean = false, predicate: (T) -> Boolean)
  fun replace(item: T): T?
  fun replaceAll(item: T)
  fun replaceAll(items: Collection<T>)
}

@Stable
private class SnapshotStateBackStack<T> : BackStack<T> {

  private val stacks = mutableStateListOf<T>()

  override val size: Int
    get() = stacks.size

  override fun iterator(): Iterator<T> {
    return stacks.iterator()
  }

  override val canPop: Boolean
    get() = stacks.size > 1

  override fun push(item: T): Boolean {
    return stacks.add(item)
  }

  override fun pop(): T? {
    if (canPop) return stacks.removeLast()
    return null
  }

  override fun popUntil(inclusive: Boolean, predicate: (T) -> Boolean) {
    while (canPop) {
      if (predicate(stacks.last())) {
        if (inclusive) {
          stacks.removeLast()
        }
        return
      } else {
        stacks.removeLast()
      }
    }
  }

  override fun replace(item: T): T? {
    return if (stacks.isEmpty()) {
      stacks.add(item)
      null
    } else {
      val last = stacks[stacks.lastIndex]
      stacks[stacks.lastIndex] = item
      last
    }
  }

  override fun replaceAll(item: T) {
    stacks.clear()
    stacks.add(item)
  }

  override fun replaceAll(items: Collection<T>) {
    stacks.clear()
    stacks.addAll(items)
  }
}

@StateFactoryMarker
fun <T> mutableBackStackOf(): BackStack<T> = SnapshotStateBackStack()

@StateFactoryMarker
fun <T> mutableBackStackOf(vararg elements: T): BackStack<T> =
  SnapshotStateBackStack<T>().also { it.replaceAll(elements.toList()) }

@StateFactoryMarker
fun <T> mutableBackStackOf(elements: Collection<T>): BackStack<T> =
  SnapshotStateBackStack<T>().also { it.replaceAll(elements) }
