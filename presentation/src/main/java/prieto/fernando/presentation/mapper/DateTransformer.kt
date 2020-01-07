package prieto.fernando.presentation.mapper

import org.joda.time.DateTime
import org.joda.time.Days
import javax.inject.Inject
import kotlin.math.absoluteValue

interface DateTransformer {
    fun dateToDateString(dateTime: DateTime): String
    fun getDifferenceOfDays(dateTime: DateTime): String
    fun isPast(launchDate: DateTime): Boolean
}

class DateTransformerImpl @Inject constructor(
    private val dateTimeProvider: DateTimeProvider
) : DateTransformer {
    override fun dateToDateString(dateTime: DateTime): String {
        val date =
            "${dateTime.dayOfMonth().getStringValue()}-${dateTime.monthOfYear().getStringValue()}-${dateTime.yearOfEra().getStringValue()}"
        val time =
            "${dateTime.hourOfDay.getStringValue()}:${dateTime.minuteOfHour().getStringValue()}"
        return "$date at $time"
    }

    override fun getDifferenceOfDays(dateTime: DateTime): String {
        val daysDifference = Days.daysBetween(dateTimeProvider.today(), dateTime)
        return daysDifference.days.absoluteValue.toString()
    }

    override fun isPast(launchDate: DateTime): Boolean =
        launchDate.isBefore(dateTimeProvider.today())

    private fun DateTime.Property.getStringValue() = get().getTwoDigitsValue()

    private fun Int.getStringValue() = getTwoDigitsValue()

    private fun Int.getTwoDigitsValue() =
        if (this < 10) "0$this" else this.toString()
}
