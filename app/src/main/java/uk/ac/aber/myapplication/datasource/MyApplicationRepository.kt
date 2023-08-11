package uk.ac.aber.myapplication.datasource

import android.app.Application
import uk.ac.aber.myapplication.model.Gender
import uk.ac.aber.myapplication.model.Student
import java.time.LocalDateTime

class MyApplicationRepository(application: Application) {
    private val studentDao = MyApplicationRoomDatabase.getDatabase(application)!!.studentDao()

    suspend fun insert(student: Student){
        studentDao.insertSingleStudent(student)
    }

    suspend fun insertMultipleStudents(students: List<Student>){
        studentDao.insertMultipleStudents(students)
    }
    suspend fun insertSingleStudent(student: Student){
        studentDao.insertSingleStudent(student)
    }

    fun getRecentStudents(
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ) = studentDao.getStudentsAdmittedBetweenDates(startDate, endDate)

    fun getAllStudents() = studentDao.getAllStudents()

    fun getStudentsOrderedByFirstName() = studentDao.getStudentsOrderedByFirstName()
    fun getStudentsOrderedByLastName() = studentDao.getStudentsOrderedByLastName()
    fun getStudentsOrderedByPhoneNumber() = studentDao.getStudentsOrderedByPhoneNumber()

    suspend fun deleteStudent(student: Student) {
        studentDao.deleteStudent(student)
    }

    fun getStudentsByGender(
        gender: Gender
    ) = studentDao.getStudentsByGender(gender)

    fun searchStudentsByName(query : String) = studentDao.searchStudentsByName(query)
}