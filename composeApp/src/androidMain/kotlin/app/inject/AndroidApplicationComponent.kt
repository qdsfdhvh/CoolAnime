package app.inject

import android.app.Application
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.cache.memory.maxSizePercent
import com.seiko.imageloader.component.setupDefaultComponents
import com.seiko.imageloader.intercept.bitmapMemoryCacheConfig
import com.seiko.imageloader.intercept.imageMemoryCacheConfig
import com.seiko.imageloader.intercept.painterMemoryCacheConfig
import com.seiko.imageloader.option.androidContext
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import okio.Path.Companion.toOkioPath

@ApplicationScope
@Component
abstract class AndroidApplicationComponent(
  @get:Provides val application: Application,
) : ApplicationComponent {

  @ApplicationScope
  @Provides
  fun provideImageLoader() = ImageLoader {
    options {
      androidContext(application)
    }
    components {
      setupDefaultComponents()
    }
    interceptor {
      // cache 25% memory bitmap
      bitmapMemoryCacheConfig {
        maxSizePercent(application, 0.25)
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
        directory(application.cacheDir.resolve("image_cache").toOkioPath())
        maxSizeBytes(512L * 1024 * 1024) // 512MB
      }
    }
  }

  companion object
}
