package presentation.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seiko.anime.compiler.annotations.BindUi
import presentation.component.state.checkState
import presentation.route.HomeScreen
import presentation.widget.AnimeGridHead
import presentation.widget.ExpandedAnimeCard

@BindUi(HomeScreen::class, HomeUiState::class)
@Composable
fun HomeUi(
  state: HomeUiState,
  modifier: Modifier,
) {
  val eventSink = state.eventSink
  LazyVerticalGrid(
    columns = GridCells.FixedSize(120.dp),
    modifier = modifier
      .fillMaxSize()
      .windowInsetsPadding(WindowInsets.statusBars),
    horizontalArrangement = Arrangement.SpaceEvenly,
    verticalArrangement = Arrangement.Center,
  ) {
    checkState(
      state = state.recentUpdatesState,
      onRefresh = { eventSink(HomeUiEvent.RefreshRecentUpdates) },
    ) { recentUpdates ->
      item(span = { GridItemSpan(maxCurrentLineSpan) }) {
        AnimeGridHead(
          "近期更新",
          onMoreClick = {
          },
          modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth(),
        )
      }
      items(recentUpdates) { item ->
        ExpandedAnimeCard(
          anime = item,
          onClick = { eventSink(HomeUiEvent.GotoDetail(item)) },
        )
      }
    }
  }
}
