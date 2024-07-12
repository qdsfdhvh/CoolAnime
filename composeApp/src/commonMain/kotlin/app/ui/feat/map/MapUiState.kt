package app.ui.feat.map

import app.ui.component.maps.widget.AppMapState
import app.ui.component.voyager.VoyagerUiState

data class MapUiState(
  val mapState: AppMapState,
) : VoyagerUiState
