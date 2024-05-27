package app.ui.component.navigation.runtime

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.currentCompositeKeyHash
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import app.ui.component.navigation.presenter.PresenterViewModel
import app.ui.component.navigation.screen.Screen
import app.util.platform.Parcelable
import app.util.platform.Parcelize

@Parcelize
data class BackStackEntry internal constructor(
  val key: String,
  val screen: Screen,
) : Parcelable, LifecycleOwner, ViewModelStoreOwner {

  private val lifecycleRegistry by lazy {
    LifecycleRegistry.createUnsafe(this)
  }

  override val lifecycle: Lifecycle
    get() = lifecycleRegistry

  override val viewModelStore by lazy {
    ViewModelStore()
  }

  fun active() {
    lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
  }

  fun inActive() {
    lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
  }

  fun destroy() {
    lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    viewModelStore.clear()
  }

  @Composable
  fun Content(navigator: Navigator) {
    val factoryManager = LocalNavigation.current

    val hash = currentCompositeKeyHash
    val viewModelKey = remember(hash) { key + hash.toString(32) }

    val presenterViewModel = viewModelStore.getOrPut(viewModelKey) {
      PresenterViewModel(
        screen = screen,
        navigator = navigator,
        factoryManager = factoryManager,
      )
    }

    val state by presenterViewModel.bodyFlow.collectAsState()
    state?.let {
      remember { factoryManager.crateUi(screen) }?.Content(it, Modifier)
    }
  }
}

private fun <T : ViewModel> ViewModelStore.getOrPut(key: String, defaultValue: () -> T): T {
  val value = get(key)
  return if (value == null) {
    val answer = defaultValue()
    put(key, answer)
    answer
  } else {
    @Suppress("UNCHECKED_CAST")
    value as T
  }
}
