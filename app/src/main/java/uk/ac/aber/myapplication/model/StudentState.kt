package uk.ac.aber.myapplication.model

import java.time.DayOfWeek
import java.time.LocalTime

data class StudentState(
    val students: List<Student> = emptyList(),
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val phoneNumberParent1: String ="",
    val phoneNumberParent2: String ="",
    val gender: Gender = Gender.MALE,
    val day: DayOfWeek = DayOfWeek.SUNDAY,
    val hour: LocalTime = LocalTime.of(10,0),
    val price: Int = 0,
    val debt: Int = 0,
    val school: String ="",
    var imagePath: String = "",
    val year: Int = 0,
    val isAddingStudent: Boolean = false,
    val sortType: SortType = SortType.FIRST_NAME
)
