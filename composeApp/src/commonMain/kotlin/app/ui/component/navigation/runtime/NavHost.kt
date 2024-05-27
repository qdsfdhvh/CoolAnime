package app.ui.component.navigation.runtime

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner

@Composable
fun NavHost(
  backStack: BackStack<BackStackEntry>,
  navigator: Navigator,
  modifier: Modifier = Modifier,
) {
  val stateHolder = rememberSaveableStateHolder()
  CompositionLocalProvider(
    LocalViewModelStoreOwner provides navigator,
  ) {
    // TODO maybe do somethings
    val lastEntry by remember { derivedStateOf { backStack.last() } }
    Crossfade(lastEntry, modifier) { entry ->
      entry.ProvideContent(stateHolder, navigator)
    }
  }
}

@Composable
fun BackStackEntry.ProvideContent(stateHolder: SaveableStateHolder, navigator: Navigator) {
  stateHolder.SaveableStateProvider(key) {
    CompositionLocalProvider(
      LocalLifecycleOwner provides this,
      LocalViewModelStoreOwner provides this,
    ) {
      Content(navigator)

      DisposableEffect(this) {
        active()
        onDispose {
          inActive()
        }
      }
    }
  }
}
