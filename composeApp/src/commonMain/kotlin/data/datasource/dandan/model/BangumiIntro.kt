package data.datasource.dandan.model

import kotlinx.serialization.Serializable

@Serializable
data class BangumiIntro(
  val animeId: Long,
  val bangumiId: String,
  val animeTitle: String = "",
  val imageUrl: String = "",
  val searchKeyword: String = "",
  val isOnAir: Boolean,
  val airDay: Long,
  val isFavorited: Boolean,
  val isRestricted: Boolean,
  val rating: Double
)
