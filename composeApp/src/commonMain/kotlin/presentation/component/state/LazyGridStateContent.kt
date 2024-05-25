package presentation.component.state

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val defaultGridContentHeight = 300.dp

fun <T> LazyGridScope.checkState(
  state: UiState<T>,
  gridContentHeight: Dp = defaultGridContentHeight,
  onRefresh: (() -> Unit)? = null,
  content: (data: T) -> Unit,
) {
  when (state) {
    UiState.Loading -> {
      item(span = { GridItemSpan(maxLineSpan) }) {
        Box(
          Modifier
            .height(gridContentHeight)
            .fillMaxWidth(),
          contentAlignment = Alignment.Center,
        ) {
          CircularProgressIndicator()
        }
      }
    }

    is UiState.Success -> {
      content(state.data)
    }

    is UiState.Failure -> {
      item(span = { GridItemSpan(maxLineSpan) }) {
        Column(
          Modifier
            .height(gridContentHeight)
            .fillMaxWidth(),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        ) {
          onRefresh?.let {
            Button(onRefresh) {
              Text("重试")
            }
          }
          Text(state.error.message.orEmpty())
        }
      }
    }
  }
}
