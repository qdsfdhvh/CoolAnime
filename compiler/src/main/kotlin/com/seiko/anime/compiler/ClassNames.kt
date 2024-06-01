package com.seiko.anime.compiler

import com.squareup.kotlinpoet.ClassName

val ComposableAnnotation = ClassName("androidx.compose.runtime", "Composable")
val ModifierClassName = ClassName("androidx.compose.ui", "Modifier")

val InjectAnnotation = ClassName("me.tatarka.inject.annotations", "Inject")
val AssistedAnnotation = ClassName("me.tatarka.inject.annotations", "Assisted")
val IntoSetClassName = ClassName("me.tatarka.inject.annotations", "IntoSet")
val ProvidesClassName = ClassName("me.tatarka.inject.annotations", "Provides")
val ActivityScopeClassName = ClassName("app.inject", "ActivityScope")

val UiPresenterClassName = ClassName("app.ui.component.voyager", "UiPresenter")
val UiPresenterFactoryClassName = ClassName("app.ui.component.voyager", "UiPresenter", "Factory")
val UiScreenClassName = ClassName("app.ui.component.voyager", "UiScreen")
val UiScreenFactoryClassName = ClassName("app.ui.component.voyager", "UiScreen", "Factory")

val MoleculeUiPresenterClassName = ClassName("app.ui.component.voyager", "MoleculeUiPresenter")
val MoleculeUiScreenClassName = ClassName("app.ui.component.voyager", "MoleculeUiScreen")

val ScreenProviderClassName = ClassName("cafe.adriel.voyager.core.registry", "ScreenProvider")
val NavigatorClassName = ClassName("cafe.adriel.voyager.navigator", "Navigator")
val ProviderNavigatorClassName = ClassName("app.ui.component.voyager", "ProviderNavigator")

val VoyagerUiStateClassName = ClassName("app.ui.component.voyager", "VoyagerUiState")
