package presentation.route

import com.slack.circuit.runtime.screen.Screen
import presentation.platform.Parcelize

@Parcelize
object HomeScreen : AppScreen("Home")

@Parcelize
data class DetailScreen(val id: Long) : AppScreen("Detail")

abstract class AppScreen(val name: String) : Screen {
  open val arguments: Map<String, *>? = null
}
