package app.inject

import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.component.setupDefaultComponents
import com.seiko.imageloader.intercept.bitmapMemoryCacheConfig
import com.seiko.imageloader.intercept.imageMemoryCacheConfig
import com.seiko.imageloader.intercept.painterMemoryCacheConfig
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import okio.Path.Companion.toOkioPath
import java.io.File

@ApplicationScope
@Component
abstract class DesktopApplicationComponent : ApplicationComponent {

  @ApplicationScope
  @Provides
  fun provideImageLoader() = ImageLoader {
    components {
      setupDefaultComponents()
    }
    interceptor {
      // cache 32MB bitmap
      bitmapMemoryCacheConfig {
        // 32MB
        maxSize(32 * 1024 * 1024)
      }
      // cache 50 image
      imageMemoryCacheConfig {
        maxSize(50)
      }
      // cache 50 painter
      painterMemoryCacheConfig {
        maxSize(50)
      }
      diskCacheConfig {
        directory(getCacheDir().toOkioPath().resolve("image_cache"))
        maxSizeBytes(512L * 1024 * 1024) // 512MB
      }
    }
  }

  companion object
}

private enum class OperatingSystem {
  Windows,
  Linux,
  MacOS,
  Unknown,
}

private val currentOperatingSystem: OperatingSystem
  get() {
    val operSys = System.getProperty("os.name").lowercase()
    return if (operSys.contains("win")) {
      OperatingSystem.Windows
    } else if (operSys.contains("nix") || operSys.contains("nux") ||
      operSys.contains("aix")
    ) {
      OperatingSystem.Linux
    } else if (operSys.contains("mac")) {
      OperatingSystem.MacOS
    } else {
      OperatingSystem.Unknown
    }
  }

private fun getCacheDir() = when (currentOperatingSystem) {
  OperatingSystem.Windows -> File(System.getenv("AppData"), "$APPLICATION_NAME/cache")
  OperatingSystem.Linux -> File(System.getProperty("user.home"), ".cache/$APPLICATION_NAME")
  OperatingSystem.MacOS -> File(System.getProperty("user.home"), "Library/Caches/$APPLICATION_NAME")
  else -> throw IllegalStateException("Unsupported operating system")
}

private const val APPLICATION_NAME = "CoolAnime"
