package presentation.component.paging

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

fun <T : Any> LazyListScope.items(
  pageData: LazyPagingItems<T>,
  itemContent: @Composable LazyItemScope.(Int, T) -> Unit,
) {
  when (val refreshLoadState = pageData.loadState.refresh) {
    LoadState.Loading -> {
      item {
        LoadingContent(Modifier.fillParentMaxSize())
      }
    }

    is LoadState.NotLoading -> {
      if (pageData.itemCount > 0) {
        items(pageData.itemCount) { index ->
          pageData[index]?.let { item ->
            itemContent(index, item)
          }
        }
      } else {
        item {
          Box(Modifier.fillParentMaxSize(), Alignment.Center) {
            Text("没有数据")
          }
        }
      }

      when (pageData.loadState.append) {
        is LoadState.Error -> {
          item {
            RetryContent(
              onClick = { pageData.retry() },
              modifier = Modifier
                .fillParentMaxWidth()
                .padding(vertical = 16.dp),
            )
          }
        }

        is LoadState.Loading -> {
          item {
            LoadingContent(Modifier.fillParentMaxWidth().padding(vertical = 16.dp))
          }
        }

        is LoadState.NotLoading -> Unit
      }
    }

    is LoadState.Error -> {
      item {
        RefreshContent(
          text = refreshLoadState.error.message.orEmpty(),
          onClick = { pageData.refresh() },
          modifier = Modifier.fillParentMaxSize(),
        )
      }
    }
  }
}

@Composable
fun LoadingContent(
  modifier: Modifier = Modifier,
) {
  Box(
    modifier = modifier,
    contentAlignment = Alignment.Center,
  ) {
    CircularProgressIndicator()
  }
}

@Composable
fun RetryContent(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Box(modifier, Alignment.Center) {
    IconButton(onClick) {
      Icon(Icons.Default.Refresh, contentDescription = "重试")
    }
  }
}

@Composable
fun RefreshContent(
  text: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier,
  ) {
    Text(text)
    Spacer(Modifier.height(8.dp))
    Button(onClick = onClick) {
      Text("重试")
    }
  }
}
