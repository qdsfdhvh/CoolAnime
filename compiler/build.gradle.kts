plugins {
  alias(libs.plugins.kotlinJvm)
}

java {
  sourceCompatibility = JavaVersion.toVersion(libs.versions.jvmToolchain.get())
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(libs.versions.jvmToolchain.get()))
  }
}

kotlin {
  sourceSets.all {
    languageSettings {
      optIn("com.google.devtools.ksp.KspExperimental")
    }
  }
}

dependencies {
  compileOnly(libs.ksp.api)
  implementation(project(":compiler:annotations"))
  implementation(libs.kotlinpoet.ksp)
}
