plugins {
  alias(libs.plugins.androidApplication) apply false
  alias(libs.plugins.androidLibrary) apply false
  alias(libs.plugins.kotlinMultiplatform) apply false
  alias(libs.plugins.jetbrainsCompose) apply false
  alias(libs.plugins.jetbrainsComposeCompiler) apply false
  alias(libs.plugins.spotless)
}

subprojects {
  configurations.all {
    resolutionStrategy {
      force("org.jetbrains.kotlin:kotlin-reflect:${libs.versions.kotlin.get()}")
      force("org.jetbrains.kotlin:kotlin-stdlib:${libs.versions.kotlin.get()}")
      force("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${libs.versions.kotlin.get()}")
      force("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${libs.versions.kotlin.get()}")
    }
  }
}

spotless {
  kotlin {
    target("*.kt", "**/*.kt")
    targetExclude("build/", "**/build/")
    ktlint(libs.versions.ktlint.get())
      .editorConfigOverride(editorConfigOverride)
  }
  kotlinGradle {
    target("*.gradle.kts", "**/*.gradle.kts")
    targetExclude("build/", "**/build/")
    ktlint(libs.versions.ktlint.get())
      .editorConfigOverride(editorConfigOverride)
  }
}

val editorConfigOverride = mapOf(
  "ktlint_function_naming_ignore_when_annotated_with" to "Composable",
  "ktlint_standard_no-wildcard-imports" to "disabled",
)
