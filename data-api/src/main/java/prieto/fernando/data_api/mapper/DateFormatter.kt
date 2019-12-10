package prieto.fernando.data_api.mapper

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

interface DateFormatter {
    fun format(dateValue: String): Date
}

class DateFormatterImpl @Inject constructor() : DateFormatter {
    override fun format(dateValue: String): Date {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        return dateFormat.parse(dateValue.replace("Z", "+0000"))
    }
}
