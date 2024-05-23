package presentation.ui.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.seiko.anime.compiler.annotations.BindPresenter
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import presentation.route.DetailScreen

@BindPresenter(DetailScreen::class)
@Inject
class DetailPresenter(
  @Assisted private val navigator: Navigator,
  @Assisted private val screen: DetailScreen,
  private val httpClient: Lazy<HttpClient>,
) : Presenter<DetailUiState> {
  @Composable
  override fun present(): DetailUiState {
    val scope = rememberCoroutineScope()
    var content by remember { mutableStateOf("") }
    return DetailUiState(
      id = screen.id,
      content = content,
      eventSink = { event ->
        when (event) {
          DetailUiEvent.Back -> {
            navigator.pop()
          }
          DetailUiEvent.Request -> {
            scope.launch(Dispatchers.IO) {
              content = httpClient.value.get("https://www.yhmgo.com/").bodyAsText()
            }
          }
        }
      },
    )
  }
}
