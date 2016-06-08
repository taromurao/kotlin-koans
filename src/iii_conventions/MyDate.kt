package iii_conventions

import java.text.SimpleDateFormat
import java.util.*

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int): Comparable<MyDate> {

    override fun compareTo(other: MyDate): Int = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }

    operator fun rangeTo(other: MyDate): DateRange = DateRange(this, other)

    fun toDate() = SimpleDateFormat("yyyy-MM-dd").parse("$year-${"%02d".format(month)}-${"%02d".format(dayOfMonth)}")

    fun nextDay(): MyDate {
        val cal = Calendar.getInstance()
        cal.time = this.toDate()
        cal.add(Calendar.DATE, 1)
        return MyDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
    }

    operator fun plus(interval: TimeInterval): MyDate {
        return when (interval) {
            TimeInterval.YEAR -> addTimeIntervals(TimeInterval.YEAR, 1)
            TimeInterval.WEEK -> addTimeIntervals(TimeInterval.WEEK, 1)
            TimeInterval.DAY -> addTimeIntervals(TimeInterval.DAY, 1)
        }
    }

    operator fun plus(interval: RepeatedTimeInterval): MyDate {
        return this.addTimeIntervals(interval.interval, interval.repetition)
    }
}

class RepeatedTimeInterval(val interval: TimeInterval, val repetition: Int)

enum class TimeInterval { DAY, WEEK, YEAR }

operator fun TimeInterval.times(i: Int) = RepeatedTimeInterval(this, i)

class DateRange(override val start: MyDate, override val endInclusive: MyDate):
        ClosedRange<MyDate>, Iterable<MyDate> {

    override fun iterator(): Iterator<MyDate> = MyDateIterator(this)

    class MyDateIterator(val range: DateRange): Iterator<MyDate> {
        private var current: MyDate = range.start

        override fun next(): MyDate {
            val result = current
            current = current.nextDay()
            return result
        }

        override fun hasNext(): Boolean = current <= range.endInclusive
    }

}
