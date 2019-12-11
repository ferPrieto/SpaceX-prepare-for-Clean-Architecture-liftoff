package prieto.fernando.domain.mapper

import java.util.*
import javax.inject.Inject
import kotlin.math.absoluteValue

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
            "${calendar.get(Calendar.DAY_OF_MONTH)}-${calendar.get(Calendar.MONTH) + 1}-${calendar.get(
                Calendar.YEAR
            )}"
        val time = "${calendar.getHour()}:${calendar.getMinutes()}"
        return "$date at $time"
    }

    private fun Calendar.getHour(): String {
        val minutes = get(Calendar.HOUR_OF_DAY)
        return minutes.getStringValue()
    }

    private fun Calendar.getMinutes(): String {
        val minutes = get(Calendar.MINUTE)
        return minutes.getStringValue()
    }

    override fun getDifferenceOfDays(date: Date): String {
        val today = Date()
        val daysDifference = (today.time - date.time) / (1000 * 60 * 60 * 24)
        return daysDifference.absoluteValue.toString()
    }

    override fun isPast(launchDate: Date): Boolean {
        val today = Date()
        return launchDate.before(today)
    }

    private fun Int.getStringValue() = if (this < 10) "0$this" else this.toString()
}
