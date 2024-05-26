package com.seiko.anime.compiler.annotations

import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
annotation class BindPresenter(
  val screen: KClass<*>,
)
