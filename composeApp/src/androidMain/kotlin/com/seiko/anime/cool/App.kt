package com.seiko.anime.cool

import android.app.Application
import presentation.inject.AndroidApplicationComponent
import presentation.inject.create

class App : Application() {

  lateinit var component: AndroidApplicationComponent

  override fun onCreate() {
    super.onCreate()
    component = AndroidApplicationComponent.create(this)
  }
}
