package presentation.route

import com.slack.circuit.runtime.screen.Screen
import presentation.platform.Parcelize

@Parcelize
object HomeScreen : AppScreen("Home")

abstract class AppScreen(val name: String) : Screen {
  open val arguments: Map<String, *>? = null
}
