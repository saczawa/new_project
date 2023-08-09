package uk.ac.aber.myapplication.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime


@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int = 0,

    var firstName: String,
    var lastName: String,
    var phoneNumber: String,

    var phoneNumberParent1: String,

    var phoneNumberParent2: String,

    var gender: Gender = Gender.MALE,

    var day: DayOfWeek = DayOfWeek.SUNDAY,

    var hour: LocalTime = LocalTime.of(10,0),

    var debt: Int,

    var price: Int,

    var school: String,

    var year: Int,

    var howLongStudent: LocalDateTime = LocalDateTime.now(),

    var startDate: LocalDateTime = LocalDateTime.now(),

    @ColumnInfo(name = "main_image_path")
    var imagePath: String,

)