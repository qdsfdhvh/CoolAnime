package com.seiko.anime.cool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.rememberCircuitNavigator
import presentation.inject.AndroidActivityComponent
import presentation.inject.create
import presentation.route.HomeScreen

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    enableEdgeToEdge()
    super.onCreate(savedInstanceState)
    val applicationComponent = (application as App).component
    val activityComponent = AndroidActivityComponent.create(this, applicationComponent)
    setContent {
      val backStack = rememberSaveableBackStack(listOf(HomeScreen))
      val navigator = rememberCircuitNavigator(backStack)
      activityComponent.rootContent.Content(
        backStack = backStack,
        navigator = navigator,
        modifier = Modifier,
      )
    }
  }
}
