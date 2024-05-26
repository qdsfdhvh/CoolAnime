package app.route

import app.ui.component.navigation.screen.Screen
import app.util.platform.Parcelize

@Parcelize
object HomeScreen : AppScreen("Home")

@Parcelize
object ScheduleScreen : AppScreen("Schedule")

@Parcelize
object MineScreen : AppScreen("Mine")

@Parcelize
data class DetailScreen(val id: Int) : AppScreen("Detail")

@Parcelize
object CounterScreen : AppScreen("Counter")

abstract class AppScreen(val name: String) : Screen {
  open val arguments: Map<String, *>? = null
}
