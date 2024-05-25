package presentation.widget

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import presentation.ui.theme.CoolAnimeFontFamilies.heiFontFamily
import presentation.ui.theme.basicBlack
import presentation.ui.theme.brightNeutral05
import presentation.ui.theme.brightNeutral06
import presentation.ui.theme.neutral05
import presentation.ui.theme.pink50

@Composable
fun AnimeGridHead(
  headline: String?,
  onMoreClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    if (headline == null) {
      Box(
        modifier = Modifier
          .size(128.dp, 20.dp)
          .clip(MaterialTheme.shapes.small)
          .background(shimmerBrush(x = 128.dp, y = 20.dp, brightNeutral06)),
      )
      Box(
        modifier = Modifier
          .size(64.dp, 16.dp)
          .clip(MaterialTheme.shapes.small)
          .background(shimmerBrush(x = 128.dp, y = 16.dp, brightNeutral05)),
      )
    } else {
      StylizedHead(text = headline)
      MoreInfo(onClick = onMoreClick)
    }
  }
}

@Composable
private fun MoreInfo(
  onClick: () -> Unit,
) {
  Row(
    modifier = Modifier.clickable(
      interactionSource = remember { MutableInteractionSource() },
      indication = null,
      role = Role.Button,
      onClick = onClick,
    ),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(
      text = "更多",
      color = neutral05,
      style = MaterialTheme.typography.bodySmall,
    )
    Icon(
      Icons.AutoMirrored.Rounded.ArrowForwardIos,
      modifier = Modifier.size(16.dp),
      contentDescription = null,
      tint = neutral05,
    )
  }
}

@Composable
private fun StylizedHead(
  text: String,
  modifier: Modifier = Modifier,
) {
  Box(modifier.width(IntrinsicSize.Max)) {
    Box(
      Modifier
        .align(Alignment.BottomStart)
        .fillMaxWidth(if (text.length < 3) 1f else 0.7f)
        .height(7.dp)
        .background(
          brush = Brush.horizontalGradient(
            listOf(
              pink50,
              pink50.copy(alpha = 0f),
            ),
          ),
          shape = CircleShape,
        ),
    )
    Text(
      modifier = Modifier.padding(bottom = 1.dp),
      text = text,
      fontFamily = heiFontFamily,
      fontWeight = FontWeight.Bold,
      color = basicBlack,
      style = MaterialTheme.typography.titleMedium,
    )
  }
}

@Composable
fun shimmerBrush(x: Dp, y: Dp, color: Color, durationMillis: Int = 1000): Brush =
  with(LocalDensity.current) {
    shimmerBrush(Offset(x.toPx(), y.toPx()), color, durationMillis)
  }

@Composable
fun shimmerBrush(
  targetOffset: Offset,
  color: Color,
  durationMillis: Int = 1000,
): Brush {
  val shimmerColors = listOf(
    color,
    color.increaseLuminanceBy(0.1f),
    color,
  )
  val transition = rememberInfiniteTransition(label = "")

  val ratio = targetOffset.x / targetOffset.y
  val horizontalDist = 350f
  val verticalDist = horizontalDist / ratio

  val startX by transition.animateFloat(
    initialValue = 0f - horizontalDist,
    targetValue = targetOffset.x,
    animationSpec = infiniteRepeatable(animation = tween(durationMillis)),
    label = "",
  )
  val startY by transition.animateFloat(
    initialValue = 0f - verticalDist,
    targetValue = targetOffset.y,
    animationSpec = infiniteRepeatable(animation = tween(durationMillis)),
    label = "",
  )

  val endX by transition.animateFloat(
    initialValue = 0f,
    targetValue = targetOffset.x + horizontalDist,
    animationSpec = infiniteRepeatable(animation = tween(durationMillis)),
    label = "",
  )
  val endY by transition.animateFloat(
    initialValue = 0f,
    targetValue = targetOffset.y + verticalDist,
    animationSpec = infiniteRepeatable(animation = tween(durationMillis)),
    label = "",
  )

  return Brush.linearGradient(
    colors = shimmerColors,
    start = Offset(x = startX, y = startY),
    end = Offset(x = endX, y = endY),
  )
}

private fun Color.increaseLuminanceBy(factor: Float) =
  Color(
    alpha = alpha,
    red = (red * (1 + factor)).coerceIn(0f, 255f),
    green = (green * (1 + factor)).coerceIn(0f, 255f),
    blue = (blue * (1 + factor)).coerceIn(0f, 255f),
  )
