package app.ui.widget

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seiko.anime.Res
import io.github.alexzhirkevich.compottie.LottieAnimation
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LottieIcon(
  iconPath: String,
  selected: Boolean,
  modifier: Modifier = Modifier,
) {
  val spec by produceState<LottieCompositionSpec?>(null) {
    val jsonString = withContext(Dispatchers.IO) {
      Res.readBytes(iconPath).decodeToString()
    }
    value = LottieCompositionSpec.JsonString(jsonString)
  }
  spec?.let {
    val animatedIcon by rememberLottieComposition(it)
    val animationProgress: Float by animateFloatAsState(
      targetValue = if (selected) 1f else 0f,
      animationSpec = tween(800, easing = LinearEasing),
      label = "",
    )
    LottieAnimation(
      animatedIcon,
      progress = {
        if (selected) animationProgress else 0f
      },
      modifier = modifier.size(24.dp),
    )
  }
}
