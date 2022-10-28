package masterj3y.github.mamadmail.common.extensions

import java.util.*

private const val SECOND_MILLIS = 1000
private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
private const val DAY_MILLIS = 24 * HOUR_MILLIS

fun Date.formatSimpleTime(): String {

    if (time < 1000000000000L) {
        time *= 1000
    }

    val now = Date().time

    val diff = now - time

    return when {
        diff < MINUTE_MILLIS -> "Now"
        diff < 2 * MINUTE_MILLIS -> "1M"
        diff < 50 * MINUTE_MILLIS -> "${diff / MINUTE_MILLIS}M"
        diff < 90 * MINUTE_MILLIS -> "1H"
        diff < 24 * HOUR_MILLIS -> "${diff / HOUR_MILLIS}H"
        diff < 48 * HOUR_MILLIS -> "1D"
        else -> "${diff / DAY_MILLIS}D"
    }
}