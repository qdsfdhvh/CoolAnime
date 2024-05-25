// Copyright 2024, Christopher Banes and the Tivi project contributors
// SPDX-License-Identifier: Apache-2.0

plugins {
  alias(libs.plugins.kotlinMultiplatform)
  alias(libs.plugins.androidLibrary)
  alias(libs.plugins.jetbrainsCompose)
  alias(libs.plugins.jetbrainsComposeCompiler)
}

kotlin {
  sourceSets {
    androidTarget()
    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    commonMain {
      dependencies {
        api(libs.androidx.paging.common)
        api(compose.runtime)
      }
    }
  }
  jvmToolchain(libs.versions.jvmToolchain.get().toInt())
}

android {
  namespace = "androidx.paging.compose"
  compileSdk = libs.versions.android.compileSdk.get().toInt()
  defaultConfig {
    minSdk = libs.versions.android.minSdk.get().toInt()
  }
}
