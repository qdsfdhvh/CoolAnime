package app.ui.feat.detail

import androidx.compose.runtime.Composable
import app.route.DetailScreen
import app.ui.component.voyager.ProviderNavigator
import com.seiko.anime.compiler.annotations.BindPresenter
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@BindPresenter(DetailScreen::class)
@Inject
@Composable
fun DetailPresenter(
  @Assisted navigator: ProviderNavigator,
  @Assisted screen: DetailScreen,
): DetailUiState {
  // val scope = rememberCoroutineScope()
  // var content by remember { mutableStateOf("") }
  return DetailUiState(
    id = screen.id,
    content = "",
    eventSink = { event ->
      when (event) {
        DetailUiEvent.Request -> {
          // scope.launch(Dispatchers.IO) {
          //   content = httpClient.value.get("https://www.yhmgo.com/").bodyAsText()
          // }
        }
      }
    },
  )
}
