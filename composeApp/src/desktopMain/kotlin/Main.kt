import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import app.inject.DesktopApplicationComponent
import app.inject.DesktopWindowComponent
import app.inject.create
import app.route.HomeScreen
import app.ui.component.navigation.runtime.Navigator
import app.ui.component.navigation.runtime.rememberSaveableBackStack

fun main() = application {
  val applicationComponent = remember {
    DesktopApplicationComponent.create()
  }

  val backStack = rememberSaveableBackStack(HomeScreen)
  val navigator = remember { Navigator(backStack) }

  Window(
    onCloseRequest = ::exitApplication,
    title = "CoolAnime",
    onKeyEvent = { event ->
      when {
        event.key == Key.Escape -> {
          if (navigator.canPop) {
            navigator.pop()
            true
          } else {
            false
          }
        }
        else -> false
      }
    },
  ) {
    val windowComponent = remember(applicationComponent) {
      DesktopWindowComponent.create(window, applicationComponent)
    }
    windowComponent.rootContent.Content(
      backStack = backStack,
      navigator = navigator,
      modifier = Modifier,
    )
  }
}
