package uk.ac.aber.myapplication.datasource.util

import androidx.room.TypeConverter
import uk.ac.aber.myapplication.model.Gender

object GenderConverter {
    @TypeConverter
    @JvmStatic
    fun toString(gender: Gender) = gender.toString()

    @TypeConverter
    @JvmStatic
    fun toGender(gender: String) = Gender.valueOf(gender)
}