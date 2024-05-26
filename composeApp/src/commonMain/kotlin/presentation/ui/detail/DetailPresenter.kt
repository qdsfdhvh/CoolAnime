package presentation.ui.detail

import androidx.compose.runtime.Composable
import co.touchlab.kermit.Logger
import com.seiko.anime.compiler.annotations.CircuitInject
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import presentation.route.DetailScreen

@CircuitInject(DetailScreen::class)
@Inject
class DetailPresenter(
  @Assisted private val navigator: Navigator,
  @Assisted private val screen: DetailScreen,
  // private val httpClient: Lazy<HttpClient>,
) : Presenter<DetailUiState> {
  init {
    Logger.d("DetailPresenter") { "init" }
  }

  @Composable
  override fun present(): DetailUiState {
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
}
