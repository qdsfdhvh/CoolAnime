package com.seiko.anime.compiler.annotations

import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
annotation class BindUi(
  val screen: KClass<*>,
  val state: KClass<*>,
)
