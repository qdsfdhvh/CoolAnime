package app.ui.widget

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
