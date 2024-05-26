package com.seiko.anime.cool

import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import app.route.HomeScreen
import app.ui.component.navigation.runtime.Navigator
import app.ui.feat.root.RootContent
import me.tatarka.inject.annotations.Inject
import platform.UIKit.UIViewController

typealias MainViewController = () -> UIViewController

@Inject
@Suppress("FunctionName")
fun MainViewController(
  rootContent: RootContent,
) = ComposeUIViewController {
  // val backStack = rememberSaveableBackStack(listOf(HomeScreen))
  // val navigator = rememberCircuitNavigator(backStack, onRootPop = { /* no-op */ })
  val navigator = remember { Navigator(HomeScreen) }
  rootContent.Content(
    // backStack = backStack,
    navigator = navigator,
    modifier = Modifier,
  )
}
