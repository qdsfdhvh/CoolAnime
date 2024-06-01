package com.seiko.anime.cool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import app.inject.AndroidActivityComponent
import app.inject.create

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    enableEdgeToEdge()
    super.onCreate(savedInstanceState)
    val applicationComponent = (application as App).component
    val activityComponent = AndroidActivityComponent.create(this, applicationComponent)
    setContent {
      // val backStack = rememberSaveableBackStack(HomeScreen)
      // val navigator = remember { Navigator(backStack) }
      // LaunchedEffect(navigator) {
      //   val callback = onBackPressedDispatcher.addCallback(this@MainActivity) {
      //     navigator.pop()
      //   }
      //   snapshotFlow { navigator.canPop }.collect {
      //     callback.isEnabled = it
      //   }
      // }
      activityComponent.rootContent.Content(
        // backStack = backStack,
        // navigator = navigator,
        modifier = Modifier,
      )
    }
  }
}
