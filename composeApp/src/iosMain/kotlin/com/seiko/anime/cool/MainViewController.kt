package com.seiko.anime.cool

import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
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
  // val navigator = remember { Navigator(HomeScreen) }
  rootContent.Content(
    // backStack = backStack,
    // navigator = navigator,
    modifier = Modifier,
  )
}
