package com.seiko.anime.cool

import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.rememberCircuitNavigator
import me.tatarka.inject.annotations.Inject
import platform.UIKit.UIViewController
import presentation.route.HomeScreen
import presentation.ui.root.RootContent

typealias MainViewController = () -> UIViewController

@Inject
@Suppress("FunctionName")
fun MainViewController(
  rootContent: RootContent,
) = ComposeUIViewController {
  val backStack = rememberSaveableBackStack(listOf(HomeScreen))
  val navigator = rememberCircuitNavigator(backStack, onRootPop = { /* no-op */ })
  rootContent.Content(
    backStack = backStack,
    navigator = navigator,
    modifier = Modifier,
  )
}
