package domain.model

data class AnimeShell(
  val id: Int,
  val name: String,
  val imageUrl: String? = null,
  val latestEpisode: Int,
  val status: String,
)
