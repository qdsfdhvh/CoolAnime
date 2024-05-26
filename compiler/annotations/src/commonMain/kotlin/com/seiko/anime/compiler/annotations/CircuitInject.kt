package com.seiko.anime.compiler.annotations

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
annotation class CircuitInject(
  val screen: KClass<*>,
)
