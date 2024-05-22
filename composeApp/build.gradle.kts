import org.gradle.configurationcache.extensions.capitalized
import org.gradle.kotlin.dsl.support.delegates.DependencyHandlerDelegate
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.config.LanguageFeature
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType

plugins {
  alias(libs.plugins.kotlinMultiplatform)
  alias(libs.plugins.androidApplication)
  alias(libs.plugins.jetbrainsCompose)
  alias(libs.plugins.jetbrainsComposeCompiler)
  alias(libs.plugins.kotlinPluginSerialization)
  alias(libs.plugins.kotlinPluginParcelize)
  alias(libs.plugins.googleKsp)
}

kotlin {
  androidTarget()
  jvm("desktop")
  listOf(
    iosX64(),
    iosArm64(),
    iosSimulatorArm64(),
  ).forEach { iosTarget ->
    iosTarget.binaries.framework {
      baseName = "ComposeApp"
      isStatic = true
    }
  }
  @OptIn(ExperimentalKotlinGradlePluginApi::class)
  applyHierarchyTemplate {
    common {
      group("jvmCommon") {
        withAndroidTarget()
        withJvm()
      }
      group("ios") {
        withIosX64()
        withIosArm64()
        withIosSimulatorArm64()
      }
    }
  }
  sourceSets {
    all {
      languageSettings {
        enableLanguageFeature(LanguageFeature.ExpectActualClasses.toString())
      }
    }
    commonMain {
      dependencies {
        implementation(compose.runtime)
        implementation(compose.foundation)
        implementation(compose.material)
        implementation(compose.ui)
        implementation(compose.components.resources)
        implementation(compose.components.uiToolingPreview)

        implementation(libs.bundles.kotlinx)
        implementation(libs.bundles.ktor.common)
        implementation(libs.bundles.circuit)

        implementation(libs.androidx.lifecycle.viewmodel.compose)
        implementation(libs.kotlininject.runtime)
      }
    }
    val jvmCommonMain by getting {
      dependencies {
        implementation(libs.ktor.client.okhttp)
      }
    }
    val desktopMain by getting {
      dependencies {
        implementation(compose.desktop.currentOs)
      }
    }
    androidMain {
      dependencies {
        implementation(libs.androidx.activity.compose)
      }
    }
    iosMain {
      dependencies {
        implementation(libs.ktor.client.darwin)
      }
    }
  }
  targets.configureEach {
    val isAndroidTarget = platformType == KotlinPlatformType.androidJvm
    compilations.configureEach {
      compileTaskProvider.configure {
        compilerOptions {
          if (isAndroidTarget) {
            freeCompilerArgs.addAll(
              "-P",
              "plugin:org.jetbrains.kotlin.parcelize:additionalAnnotation=presentation.platform.Parcelize",
            )
          }
        }
      }
    }
  }
  jvmToolchain(libs.versions.jvmToolchain.get().toInt())
}

dependencies {
  kspAll(libs.kotlininject.compiler)
}

fun DependencyHandlerDelegate.kspAll(dependencyNotation: Any) {
  kotlin.targets.asSequence()
    .filter { target ->
      target.platformType != KotlinPlatformType.common
    }
    .forEach { target ->
      add(
        "ksp${target.targetName.capitalized()}",
        dependencyNotation,
      )
    }
}

android {
  namespace = "com.seiko.anime.cool"
  compileSdk = libs.versions.android.compileSdk.get().toInt()

  sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
  sourceSets["main"].res.srcDirs("src/androidMain/res")
  sourceSets["main"].resources.srcDirs("src/commonMain/resources")

  defaultConfig {
    applicationId = "com.seiko.anime.cool"
    minSdk = libs.versions.android.minSdk.get().toInt()
    targetSdk = libs.versions.android.targetSdk.get().toInt()
    versionCode = 1
    versionName = "1.0"
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
  buildTypes {
    getByName("release") {
      isMinifyEnabled = false
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.toVersion(libs.versions.jvmToolchain.get())
  }
}

ksp {
  arg("me.tatarka.inject.generateCompanionExtensions", "true")
}

compose.desktop {
  application {
    mainClass = "MainKt"

    nativeDistributions {
      targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
      packageName = "com.seiko.anime.cool"
      packageVersion = "1.0.0"
    }
  }
}
