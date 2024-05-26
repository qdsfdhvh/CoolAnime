package app.ui.feat.home

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.route.HomeScreen
import app.ui.component.state.checkState
import app.ui.widget.AnimeGridHead
import app.ui.widget.ExpandedAnimeCard
import com.seiko.anime.compiler.annotations.CircuitInject

@CircuitInject(HomeScreen::class)
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
    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
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
