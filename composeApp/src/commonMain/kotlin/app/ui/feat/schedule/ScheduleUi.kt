package app.ui.feat.schedule

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.route.ScheduleScreen
import app.ui.theme.basicWhite
import app.ui.theme.darkPink60
import app.ui.theme.neutral01
import app.ui.theme.neutral08
import app.ui.theme.neutral10
import app.ui.theme.pink10
import app.ui.theme.pink50
import app.ui.theme.pink60
import app.ui.theme.pink70
import app.ui.theme.pink80
import app.ui.widget.UiStateContent
import app.util.ext.getCurrentTime
import app.util.ext.toDate
import app.util.ext.with
import com.seiko.anime.compiler.annotations.BindUi
import domain.model.AnimeShell
import kotlinx.coroutines.launch
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalFoundationApi::class)
@BindUi(ScheduleScreen::class)
@Composable
fun ScheduleUi(
  state: ScheduleUiState,
  modifier: Modifier,
) {
  val eventSink = state.eventSink
  UiStateContent(
    state.weeklySchedule,
    modifier = modifier.fillMaxSize().windowInsetsPadding(WindowInsets.statusBars),
    onRefreshClicked = { eventSink(ScheduleEvent.Refresh) },
  ) { weeklySchedule ->
    Column(Modifier.fillMaxSize()) {
      val pagerState = rememberPagerState { weeklySchedule.size }
      val scope = rememberCoroutineScope()
      Spacer(Modifier.height(16.dp))
      DayOfWeekBar(
        selectDayOfWeekIndex = pagerState.currentPage,
        onItemDayOfWeekIndexClick = {
          scope.launch {
            pagerState.animateScrollToPage(it)
          }
        },
        modifier = Modifier.align(Alignment.CenterHorizontally),
      )
      HorizontalPager(
        pagerState,
        modifier = Modifier.fillMaxWidth().weight(1f),
      ) {
        LazyColumn(
          modifier = Modifier.fillMaxSize(),
          contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp),
        ) {
          items(
            weeklySchedule[it],
            contentType = { "schedule_item" },
            key = { it.id },
          ) { item ->
            ScheduleItem(
              item = item,
              onClick = { eventSink(ScheduleEvent.GotoDetail(item)) },
              modifier = Modifier.fillMaxWidth(),
            )
          }
          item {
            Spacer(Modifier.height(32.dp))
          }
        }
      }
    }
  }
}

@Composable
private fun DayOfWeekBar(
  selectDayOfWeekIndex: Int,
  onItemDayOfWeekIndexClick: (Int) -> Unit,
  modifier: Modifier,
) {
  val localDate = remember { getCurrentTime().toDate() }
  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    DayOfWeek.entries.forEach {
      val selected = selectDayOfWeekIndex == it.ordinal
      DayBox(
        modifier = Modifier
          .size(40.dp, 60.dp)
          .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            role = Role.RadioButton,
            onClick = { onItemDayOfWeekIndexClick(it.ordinal) },
          ),
        date = localDate.with(it),
        selected = selected,
      )
    }
  }
}

@Composable
private fun DayBox(
  modifier: Modifier = Modifier,
  selected: Boolean,
  date: LocalDate,
) {
  Column(
    modifier = modifier.background(
      if (selected) pink70 else neutral01,
      MaterialTheme.shapes.medium,
    ),
    verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text(
      text = if (date.dayOfMonth < 10) {
        "0${date.dayOfMonth}"
      } else {
        date.dayOfMonth.toString()
      },
      color = if (selected) darkPink60 else neutral08,
      style = MaterialTheme.typography.labelSmall,
    )
    Text(
      text = date.dayOfWeek.description,
      color = if (selected) pink10 else neutral08,
      style = MaterialTheme.typography.bodyMedium,
    )
  }
}

@Composable
private fun ScheduleItem(
  item: AnimeShell,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Box(
    modifier = modifier
      .drawWithCache {
        val brush = Brush.verticalGradient(
          listOf(pink80.copy(0.8f), pink50, pink80.copy(0.8f)),
        )
        val lineX = 6.dp.toPx()
        val lineWidth = 1.dp.toPx()
        val circleRadius = 4.dp.toPx()
        onDrawWithContent {
          drawContent()
          drawLine(
            brush = brush,
            start = Offset(lineX, 0f),
            end = Offset(lineX, size.height),
            strokeWidth = lineWidth,
          )
          drawCircle(
            color = pink50,
            radius = circleRadius,
            center = Offset(lineX, size.height / 2),
          )
        }
      }
      .padding(vertical = 4.dp),
  ) {
    ItemCard(
      animeShell = item,
      modifier = modifier
        .clickable(
          interactionSource = remember { MutableInteractionSource() },
          indication = null,
          role = Role.Button,
          onClick = onClick,
        )
        .fillMaxWidth()
        .padding(start = 16.dp),
    )
  }
}

@Composable
private fun ItemCard(
  animeShell: AnimeShell,
  modifier: Modifier = Modifier,
) {
  Surface(
    modifier = modifier
      .shadow(
        elevation = 6.dp,
        shape = MaterialTheme.shapes.small,
        ambientColor = pink50.copy(0.6f),
        spotColor = pink50.copy(0.6f),
      )
      .clip(MaterialTheme.shapes.small),
    color = basicWhite,
  ) {
    Row(
      modifier = Modifier.padding(6.dp, 15.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Text(
        modifier = Modifier.width(56.dp),
        text = "第${animeShell.latestEpisode}话",
        textAlign = TextAlign.Center,
        color = darkPink60,
        style = MaterialTheme.typography.labelSmall,
      )
      Box(
        modifier = Modifier
          .padding(end = 8.dp)
          .size(1.dp, 36.dp)
          .background(
            Brush.verticalGradient(
              listOf(pink60.copy(0f), pink60, pink60.copy(0f)),
            ),
          ),
      )
      Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
        Text(
          text = animeShell.name,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
          style = MaterialTheme.typography.bodyMedium,
        )
        Text(
          text = animeShell.status,
          color = neutral10,
          style = MaterialTheme.typography.labelSmall,
        )
      }
    }
  }
}

private val DayOfWeek.description: String
  get() = when (this) {
    DayOfWeek.MONDAY -> "周一"
    DayOfWeek.TUESDAY -> "周二"
    DayOfWeek.WEDNESDAY -> "周三"
    DayOfWeek.THURSDAY -> "周四"
    DayOfWeek.FRIDAY -> "周五"
    DayOfWeek.SATURDAY -> "周六"
    DayOfWeek.SUNDAY -> "周日"
    else -> ""
  }
