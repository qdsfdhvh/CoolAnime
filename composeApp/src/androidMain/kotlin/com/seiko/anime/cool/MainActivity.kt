package com.seiko.anime.cool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import app.inject.AndroidActivityComponent
import app.inject.create
import app.route.HomeScreen
import app.ui.component.navigation.runtime.Navigator
import app.ui.component.navigation.runtime.rememberSaveableBackStack

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    enableEdgeToEdge()
    super.onCreate(savedInstanceState)
    val applicationComponent = (application as App).component
    val activityComponent = AndroidActivityComponent.create(this, applicationComponent)
    setContent {
      val backStack = rememberSaveableBackStack(HomeScreen)
      val navigator = remember { Navigator(backStack) }
      LaunchedEffect(navigator) {
        val callback = onBackPressedDispatcher.addCallback(this@MainActivity) {
          navigator.pop()
        }
        snapshotFlow { navigator.canPop }.collect {
          callback.isEnabled = it
        }
      }
      activityComponent.rootContent.Content(
        backStack = backStack,
        navigator = navigator,
        modifier = Modifier,
      )
    }
  }
}
