plugins {
  alias(libs.plugins.androidApplication) apply false
  alias(libs.plugins.androidLibrary) apply false
  alias(libs.plugins.kotlinMultiplatform) apply false
  alias(libs.plugins.kotlinPluginSerialization) apply false
  alias(libs.plugins.kotlinPluginParcelize) apply false
  alias(libs.plugins.jetbrainsCompose) apply false
  alias(libs.plugins.jetbrainsComposeCompiler) apply false
  alias(libs.plugins.spotless)
}

subprojects {
  configurations.all {
    resolutionStrategy {
      eachDependency {
        if (requested.group == "org.jetbrains.kotlin" && requested.name.startsWith("kotlin-")) {
          useVersion(libs.versions.kotlin.get())
          because("force kotlin version")
        }
      }
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
