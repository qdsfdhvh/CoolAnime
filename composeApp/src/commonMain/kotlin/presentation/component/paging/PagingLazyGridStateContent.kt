package presentation.component.paging

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

private val defaultGridContentHeight = 300.dp

fun <T : Any> LazyGridScope.items(
  pageData: LazyPagingItems<T>,
  initialLoading: Boolean = true,
  itemContent: @Composable LazyGridItemScope.(Int, T) -> Unit,
) {
  when (val refreshLoadState = pageData.loadState.refresh) {
    LoadState.Loading,
    is LoadState.NotLoading,
    -> {
      if (pageData.itemCount > 0) {
        items(pageData.itemCount) { index ->
          pageData[index]?.let { item ->
            itemContent(index, item)
          }
        }
      } else {
        if (refreshLoadState is LoadState.Loading) {
          if (initialLoading) {
            item(span = { GridItemSpan(maxLineSpan) }) {
              LoadingContent(
                Modifier
                  .height(defaultGridContentHeight)
                  .fillMaxWidth(),
              )
            }
          }
        } else {
          item(span = { GridItemSpan(maxLineSpan) }) {
            Box(
              Modifier
                .height(defaultGridContentHeight)
                .fillMaxWidth(),
              contentAlignment = Alignment.Center,
            ) {
              Text("没有数据")
            }
          }
        }
      }

      when (pageData.loadState.append) {
        is LoadState.Error -> {
          item(span = { GridItemSpan(maxLineSpan) }) {
            RetryContent(
              onClick = { pageData.retry() },
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            )
          }
        }

        is LoadState.Loading -> {
          item(span = { GridItemSpan(maxLineSpan) }) {
            LoadingContent(
              Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            )
          }
        }

        is LoadState.NotLoading -> Unit
      }
    }

    is LoadState.Error -> {
      item(span = { GridItemSpan(maxLineSpan) }) {
        RefreshContent(
          text = refreshLoadState.error.message.orEmpty(),
          onClick = { pageData.refresh() },
          modifier = Modifier
            .height(defaultGridContentHeight)
            .fillMaxWidth(),
        )
      }
    }
  }
}
