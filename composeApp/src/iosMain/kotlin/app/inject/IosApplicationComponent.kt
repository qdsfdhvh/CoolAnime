package app.inject

import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.component.setupDefaultComponents
import com.seiko.imageloader.intercept.bitmapMemoryCacheConfig
import com.seiko.imageloader.intercept.imageMemoryCacheConfig
import com.seiko.imageloader.intercept.painterMemoryCacheConfig
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import okio.Path.Companion.toPath
import platform.Foundation.NSCachesDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask

@ApplicationScope
@Component
abstract class IosApplicationComponent : ApplicationComponent {

  @ApplicationScope
  @Provides
  fun provideImageLoader() = ImageLoader {
    components {
      setupDefaultComponents()
    }
    interceptor {
      // cache 32MB bitmap
      bitmapMemoryCacheConfig {
        maxSize(32 * 1024 * 1024) // 32MB
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
        directory(getCacheDir().toPath().resolve("image_cache"))
        maxSizeBytes(512L * 1024 * 1024) // 512MB
      }
    }
  }

  companion object
}

private fun getCacheDir(): String {
  return NSSearchPathForDirectoriesInDomains(
    NSCachesDirectory,
    NSUserDomainMask,
    true,
  ).first() as String
}
