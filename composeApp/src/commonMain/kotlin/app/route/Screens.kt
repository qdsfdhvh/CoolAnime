package app.route

import app.util.platform.Parcelable
import app.util.platform.Parcelize
import cafe.adriel.voyager.core.registry.ScreenProvider

@Parcelize
object HomeScreen : AppScreen("Home")

@Parcelize
object ScheduleScreen : AppScreen("Schedule")

@Parcelize
object MineScreen : AppScreen("Mine")

@Parcelize
data class DetailScreen(val id: Long) : AppScreen("Detail")

@Parcelize
data class CounterScreen(val count: Int) : AppScreen("Counter")

abstract class AppScreen(val name: String) : ScreenProvider, Parcelable {
  open val arguments: Map<String, *>? = null
}
