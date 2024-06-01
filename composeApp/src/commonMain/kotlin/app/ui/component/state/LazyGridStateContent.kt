package app.ui.component.state

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val defaultGridContentHeight = 300.dp

fun <T> LazyGridScope.checkState(
  state: UiState<T>,
  gridContentHeight: Dp = defaultGridContentHeight,
  onRefresh: (() -> Unit)? = null,
  loadingContent: @Composable () -> Unit = {
    Box(
      Modifier
        .height(gridContentHeight)
        .fillMaxWidth(),
      contentAlignment = Alignment.Center,
    ) {
      CircularProgressIndicator()
    }
  },
  failureContent: @Composable (error: Throwable) -> Unit = { error ->
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
      Text(error.message.orEmpty())
    }
  },
  content: (data: T) -> Unit,
) {
  when (state) {
    UiState.Loading -> {
      item(span = { GridItemSpan(maxLineSpan) }) {
        loadingContent()
      }
    }

    is UiState.Success -> {
      content(state.data)
    }

    is UiState.Failure -> {
      item(span = { GridItemSpan(maxLineSpan) }) {
        failureContent(state.error)
      }
    }
  }
}
