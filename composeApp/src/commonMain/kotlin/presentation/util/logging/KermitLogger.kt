package presentation.util.logging

import co.touchlab.kermit.Logger as Kermit

class KermitLogger : Logger {
  override fun d(tag: String, message: () -> String) {
    Kermit.d(null, tag = tag, message = message)
  }
}
