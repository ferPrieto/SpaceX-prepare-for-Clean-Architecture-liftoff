package prieto.fernando.domain.mapper

import java.util.*
import javax.inject.Inject

interface DateTransformer {
    fun dateToDateString(date: Date): String
    fun getDifferenceOfDays(date: Date): String
    fun isPast(launchDate: Date): Boolean
}

class DateTransformerImpl @Inject constructor() : DateTransformer {
    private val calendar = Calendar.getInstance(Locale.UK)

    override fun dateToDateString(date: Date): String {
        calendar.time = date
        val date =
            "${calendar.get(Calendar.DAY_OF_MONTH)}-${calendar.get(Calendar.MONTH)}-${calendar.get(
                Calendar.YEAR
            )}"
        val time = "${calendar.get(Calendar.HOUR)}:${calendar.get(Calendar.MINUTE)}"
        return "$date at $time"
    }

    override fun getDifferenceOfDays(date: Date): String {
        val today = Date()
        val daysDifference = (today.time - date.time) / (1000 * 60 * 60 * 24)
        return daysDifference.toString()
    }

    override fun isPast(launchDate: Date): Boolean {
        val today = Date()
        return launchDate.before(today)
    }

}
