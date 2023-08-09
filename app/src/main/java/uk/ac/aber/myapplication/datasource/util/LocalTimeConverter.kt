package uk.ac.aber.myapplication.datasource.util

import androidx.room.TypeConverter
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object LocalTimeConverter {
    @TypeConverter
    fun fromLocalTime(localTime: LocalTime?): String? {
        return localTime?.format(DateTimeFormatter.ISO_LOCAL_TIME)
    }

    @TypeConverter
    fun toLocalTime(localTimeString: String?): LocalTime? {
        return localTimeString?.let { LocalTime.parse(it, DateTimeFormatter.ISO_LOCAL_TIME) }
    }
}