import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.rememberCircuitNavigator
import presentation.inject.DesktopApplicationComponent
import presentation.inject.DesktopWindowComponent
import presentation.inject.create
import presentation.route.HomeScreen

fun main() = application {
  val applicationComponent = remember {
    DesktopApplicationComponent.create()
  }
  Window(
    onCloseRequest = ::exitApplication,
    title = "CoolAnime",
  ) {
    val windowComponent = remember(applicationComponent) {
      DesktopWindowComponent.create(window, applicationComponent)
    }

    val backStack = rememberSaveableBackStack(listOf(HomeScreen))
    val navigator = rememberCircuitNavigator(backStack, onRootPop = { /* no-op */ })
    windowComponent.rootContent.Content(
      backStack = backStack,
      navigator = navigator,
      modifier = Modifier,
    )
  }
}
