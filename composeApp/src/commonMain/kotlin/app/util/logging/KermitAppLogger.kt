package app.util.logging

import co.touchlab.kermit.Logger as Kermit

class KermitAppLogger : AppLogger {
  override fun d(tag: String, throwable: Throwable?, message: () -> String) {
    Kermit.d(throwable, tag = tag, message = message)
  }

  override fun i(tag: String, throwable: Throwable?, message: () -> String) {
    Kermit.i(throwable, tag = tag, message = message)
  }

  override fun w(tag: String, throwable: Throwable?, message: () -> String) {
    Kermit.w(throwable, tag = tag, message = message)
  }

  override fun e(tag: String, throwable: Throwable?, message: () -> String) {
    Kermit.e(throwable, tag = tag, message = message)
  }
}
