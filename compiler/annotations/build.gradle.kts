plugins {
  alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
  jvm()
  iosX64()
  iosArm64()
  iosSimulatorArm64()
  jvmToolchain(libs.versions.jvmToolchain.get().toInt())
}
