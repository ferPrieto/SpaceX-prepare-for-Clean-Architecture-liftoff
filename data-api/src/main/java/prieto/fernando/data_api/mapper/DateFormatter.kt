package prieto.fernando.data_api.mapper

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import javax.inject.Inject

interface DateFormatter {
    fun format(dateValue: String): DateTime
}

class DateFormatterImpl @Inject constructor() : DateFormatter {
    override fun format(dateValue: String): DateTime {
        val dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")

        return dateTimeFormatter.parseDateTime(dateValue.replace("Z", "+0000"))
    }
}
