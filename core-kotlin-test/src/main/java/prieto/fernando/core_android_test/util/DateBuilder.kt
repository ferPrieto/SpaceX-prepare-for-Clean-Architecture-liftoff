package prieto.fernando.core_android_test.util

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

fun buildDate(dateValue: String): DateTime =
    DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        .parseDateTime(dateValue.replace("Z", "+0000"))