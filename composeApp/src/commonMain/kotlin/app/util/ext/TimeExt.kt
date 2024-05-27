package app.util.ext

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

val ShangHaiTimeZone by lazy {
  TimeZone.of("Asia/Shanghai")
}

fun getCurrentTime(): Instant {
  return Clock.System.now()
}

fun Instant.toDate(timeZone: TimeZone = ShangHaiTimeZone): LocalDate {
  return toLocalDateTime(timeZone).date
}

fun LocalDate.with(dayOfWeek: DayOfWeek): LocalDate {
  val currentDayOfWeek = this.dayOfWeek
  val daysUntilTarget = dayOfWeek.ordinal - currentDayOfWeek.ordinal
  return this.plus(daysUntilTarget, DateTimeUnit.DAY)
}
