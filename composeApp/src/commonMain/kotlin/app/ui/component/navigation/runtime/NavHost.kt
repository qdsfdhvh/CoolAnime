package app.ui.component.navigation.runtime

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner

@Composable
fun NavHost(
  navigator: Navigator,
  modifier: Modifier = Modifier,
) {
  val stateHolder = rememberSaveableStateHolder()
  CompositionLocalProvider(
    LocalViewModelStoreOwner provides navigator,
  ) {
    Crossfade(navigator.backStacks.last(), modifier) { entry ->
      stateHolder.SaveableStateProvider(entry.key) {
        CompositionLocalProvider(
          LocalLifecycleOwner provides entry,
          LocalViewModelStoreOwner provides entry,
        ) {
          entry.Content(navigator)

          DisposableEffect(entry) {
            entry.active()
            onDispose {
              entry.inActive()
            }
          }
        }
      }
    }
  }
}
