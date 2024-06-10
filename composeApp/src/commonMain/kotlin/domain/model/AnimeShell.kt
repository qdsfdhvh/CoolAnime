package domain.model

data class AnimeShell(
  val id: Long,
  val name: String,
  val imageUrl: String? = null,
  val summary: String,
  val status: String,
)
