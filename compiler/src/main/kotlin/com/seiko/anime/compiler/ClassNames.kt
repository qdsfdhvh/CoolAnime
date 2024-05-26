package com.seiko.anime.compiler

import com.squareup.kotlinpoet.ClassName

val InjectAnnotation = ClassName("me.tatarka.inject.annotations", "Inject")
val AssistedAnnotation = ClassName("me.tatarka.inject.annotations", "Assisted")
val IntoSetClassName = ClassName("me.tatarka.inject.annotations", "IntoSet")
val ProvidesClassName = ClassName("me.tatarka.inject.annotations", "Provides")
val ActivityScopeClassName = ClassName("app.inject", "ActivityScope")

val PresenterClassName = ClassName("app.ui.component.navigation.presenter", "Presenter")
val PresenterFactoryClassName = ClassName("app.ui.component.navigation.presenter", "Presenter", "Factory")
val PresenterLambdaClassName = ClassName("app.ui.component.navigation.presenter", "presenterOf")

val UiClassName = ClassName("app.ui.component.navigation.ui", "Ui")
val UiFactoryClassName = ClassName("app.ui.component.navigation.ui", "Ui", "Factory")
val uiLambdaClassName = ClassName("app.ui.component.navigation.ui", "ui")

val ScreenClassName = ClassName("app.ui.component.navigation.screen", "Screen")
val NavigatorClassName = ClassName("app.ui.component.navigation.runtime", "Navigator")
val BaseUiStateClassName = ClassName("app.ui.component.navigation.runtime", "BaseUiState")
