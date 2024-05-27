package app.ui.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.ui.component.state.UiState

@Composable
fun <T> UiStateContent(
  loadState: UiState<T>,
  modifier: Modifier = Modifier,
  onRefreshClicked: ((() -> Unit))? = null,
  content: @Composable BoxScope.(T) -> Unit,
) {
  Box(
    modifier = modifier,
    contentAlignment = Alignment.Center,
  ) {
    when (loadState) {
      UiState.Loading -> {
        CircularProgressIndicator()
      }

      is UiState.Success -> {
        content(loadState.data)
      }

      is UiState.Failure -> {
        Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
          Text(
            loadState.error.message.orEmpty(),
            textAlign = TextAlign.Center,
          )
          if (onRefreshClicked != null) {
            Spacer(Modifier.height(8.dp))
            Button(onClick = onRefreshClicked) {
              Text(text = "重试")
            }
          }
        }
      }
    }
  }
}
