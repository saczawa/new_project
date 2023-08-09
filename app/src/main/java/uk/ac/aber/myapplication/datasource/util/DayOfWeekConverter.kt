package uk.ac.aber.myapplication.datasource.util

import androidx.room.TypeConverter
import java.time.DayOfWeek

object DayOfWeekConverter {
    @TypeConverter
    fun toDayOfWeek(value: Int?): DayOfWeek? {
        return value?.let {
            DayOfWeek.of(it)
        }
    }

    @TypeConverter
    fun fromDayOfWeek(dayOfWeek: DayOfWeek?): Int? {
        return dayOfWeek?.value
    }
}