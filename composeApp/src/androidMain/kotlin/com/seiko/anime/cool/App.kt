package com.seiko.anime.cool

import android.app.Application
import app.inject.AndroidApplicationComponent
import app.inject.create
import com.mapbox.common.MapboxOptions

class App : Application() {

  lateinit var component: AndroidApplicationComponent

  override fun onCreate() {
    super.onCreate()
    component = AndroidApplicationComponent.create(this)
    // TODO: move to other place
    MapboxOptions.accessToken = BuildConfig.MAPBOX_TOKEN
  }
}
