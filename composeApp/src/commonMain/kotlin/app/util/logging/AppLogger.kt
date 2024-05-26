package app.util.logging

interface AppLogger {
  fun d(tag: String, throwable: Throwable? = null, message: () -> String)
  fun i(tag: String, throwable: Throwable? = null, message: () -> String)
  fun w(tag: String, throwable: Throwable? = null, message: () -> String)
  fun e(tag: String, throwable: Throwable? = null, message: () -> String)
}
