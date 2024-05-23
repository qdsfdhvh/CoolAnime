package presentation.util.logging

interface Logger {
  fun d(tag: String, message: () -> String)
}
