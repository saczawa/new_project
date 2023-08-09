package uk.ac.aber.myapplication.model

import java.time.DayOfWeek
import java.time.LocalTime

sealed interface StudentEvent
{
    object SaveStudent: StudentEvent
    data class SetFirstName(val firstName: String): StudentEvent
    data class SetLastName(val lastName: String): StudentEvent
    data class SetPhoneNumber(val phoneNumber: String): StudentEvent
    data class SetPhoneNumberParent1(val phoneNumberParent1: String): StudentEvent
    data class SetPhoneNumberParent2(val phoneNumberParent2: String): StudentEvent
    data class SetGender(val gender: Gender): StudentEvent
    data class SetDay(val day: DayOfWeek): StudentEvent
    data class SetHour(val hour: LocalTime): StudentEvent
    data class SetDebt(val debt: Int): StudentEvent
    data class SetPrice(val price: Int): StudentEvent
    data class SetSchool(val school: String): StudentEvent
    data class SetYear(val year: Int): StudentEvent
    data class SetImagePath(val imagePath: String): StudentEvent
    object ShowDialog: StudentEvent
    object HideDialog: StudentEvent
    data class SortStudent(val sortType: SortType): StudentEvent
    data class DeleteStudent(val student: Student): StudentEvent
}