rootProject.name = "CoolAnime"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
  repositories {
    google {
      mavenContent {
        includeGroupAndSubgroups("androidx")
        includeGroupAndSubgroups("com.android")
        includeGroupAndSubgroups("com.google")
      }
    }
    mavenCentral()
    gradlePluginPortal()
  }
}

dependencyResolutionManagement {
  repositories {
    google {
      mavenContent {
        includeGroupAndSubgroups("androidx")
        includeGroupAndSubgroups("com.android")
        includeGroupAndSubgroups("com.google")
      }
    }
    mavenCentral()
    maven {
      url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
      credentials.username = "mapbox"
      credentials.password = providers.gradleProperty("MAPBOX_TOKEN").get()
      authentication.create<BasicAuthentication>("basic")
    }
  }
}

include(":composeApp")
include(":compiler")
include(":compiler:annotations")
include(":thirdparty:androidx:paging:compose")
