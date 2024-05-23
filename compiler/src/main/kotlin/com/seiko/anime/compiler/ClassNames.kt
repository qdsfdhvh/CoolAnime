package com.seiko.anime.compiler

import com.squareup.kotlinpoet.ClassName

val InjectAnnotation = ClassName("me.tatarka.inject.annotations", "Inject")
val AssistedAnnotation = ClassName("me.tatarka.inject.annotations", "Assisted")

val PresenterClassName = ClassName("com.slack.circuit.runtime.presenter", "Presenter")
val PresenterFactoryClassName = ClassName("com.slack.circuit.runtime.presenter", "Presenter", "Factory")

val UiClassName = ClassName("com.slack.circuit.runtime.ui", "Ui")
val UiFactoryClassName = ClassName("com.slack.circuit.runtime.ui", "Ui", "Factory")
val uiLambdaClassName = ClassName("com.slack.circuit.runtime.ui", "ui")

val NavigatorClassName = ClassName("com.slack.circuit.runtime", "Navigator")
val ScreenClassName = ClassName("com.slack.circuit.runtime.screen", "Screen")
val CircuitContextClassName = ClassName("com.slack.circuit.runtime", "CircuitContext")

val IntoSetClassName = ClassName("me.tatarka.inject.annotations", "IntoSet")
val ProvidesClassName = ClassName("me.tatarka.inject.annotations", "Provides")
val ActivityScopeClassName = ClassName("presentation.inject", "ActivityScope")