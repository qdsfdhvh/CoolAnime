package com.seiko.anime.compiler.annotations

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class BindPresenter(
  val screen: KClass<*>,
)
