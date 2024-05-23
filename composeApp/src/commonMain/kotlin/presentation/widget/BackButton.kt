package presentation.widget

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BackButton(
  modifier: Modifier = Modifier,
  onBack: () -> Unit,
) {
  IconButton(onBack, modifier) {
    Icon(Icons.AutoMirrored.Filled.ArrowBack, "back")
  }
}
