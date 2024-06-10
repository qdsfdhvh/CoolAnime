package data.util

import kotlinx.serialization.json.Json

val globalJson by lazy {
  Json {
    ignoreUnknownKeys = true
  }
}
