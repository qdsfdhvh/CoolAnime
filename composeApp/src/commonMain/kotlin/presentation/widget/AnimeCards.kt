package presentation.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.seiko.anime.Res
import com.seiko.anime.broken_image
import com.seiko.imageloader.ui.AutoSizeImage
import domain.model.AnimeShell
import org.jetbrains.compose.resources.painterResource
import presentation.ui.theme.basicBlack
import presentation.ui.theme.basicWhite
import presentation.ui.theme.darkPink60

@Composable
fun ExpandedAnimeCard(
  modifier: Modifier = Modifier,
  anime: AnimeShell,
  onClick: (Int) -> Unit,
) {
  Column(
    modifier = modifier.clickable(
      interactionSource = remember { MutableInteractionSource() },
      indication = null,
      role = Role.Button,
      onClick = { onClick(anime.id) },
    ),
  ) {
    AnimeImage(anime.imageUrl)
    Text(
      modifier = Modifier.padding(top = 6.dp),
      text = anime.name,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis,
      color = basicBlack,
      style = MaterialTheme.typography.bodySmall,
    )
    Text(
      text = "第${anime.latestEpisode}话",
      color = darkPink60,
      style = MaterialTheme.typography.labelSmall,
    )
  }
}

@Composable
fun NarrowAnimeCard(
  modifier: Modifier = Modifier,
  anime: AnimeShell,
  subTitle: String = "第${anime.latestEpisode}话",
  onClick: (Int) -> Unit,
) {
  Column(
    modifier = modifier.clickable(
      interactionSource = remember { MutableInteractionSource() },
      indication = null,
      role = Role.Button,
      onClick = { onClick(anime.id) },
    ),
    verticalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    Box(Modifier.clip(MaterialTheme.shapes.extraSmall)) {
      AnimeImage(anime.imageUrl)
      Box(
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .fillMaxWidth()
          .height(20.dp)
          .background(
            brush = Brush.verticalGradient(
              colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.5f)),
            ),
          ),
        contentAlignment = Alignment.BottomEnd,
      ) {
        Text(
          modifier = Modifier.offset((-6).dp, (-5).dp),
          text = subTitle,
          color = basicWhite,
          style = MaterialTheme.typography.labelSmall,
        )
      }
    }
    Text(
      text = anime.name,
      minLines = 2,
      maxLines = 2,
      overflow = TextOverflow.Ellipsis,
      color = basicBlack,
      style = MaterialTheme.typography.bodySmall,
    )
  }
}

@Composable
private fun AnimeImage(imageUrl: String?) {
  AutoSizeImage(
    imageUrl.orEmpty(),
    modifier = Modifier
      .aspectRatio(ratio = 0.72f)
      .clip(MaterialTheme.shapes.extraSmall),
    contentDescription = null,
    placeholderPainter = { painterResource(Res.drawable.broken_image) },
    errorPainter = { painterResource(Res.drawable.broken_image) },
  )
}
