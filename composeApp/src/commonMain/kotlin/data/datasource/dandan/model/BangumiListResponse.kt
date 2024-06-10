package data.datasource.dandan.model

import kotlinx.serialization.Serializable

@Serializable
data class BangumiListResponse(
  val bangumiList: List<BangumiIntro>,
  val success: Boolean,
  val errorCode: Int = 0,
  val errorMessage: String = "",
)
