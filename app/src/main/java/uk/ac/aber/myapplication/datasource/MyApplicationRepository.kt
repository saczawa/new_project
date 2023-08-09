package uk.ac.aber.myapplication.datasource

import android.app.Application
import uk.ac.aber.myapplication.model.Student

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

    fun getAllStudents() = studentDao.getAllStudents()

    fun getStudentsOrderedByFirstName() = studentDao.getStudentsOrderedByFirstName()
    fun getStudentsOrderedByLastName() = studentDao.getStudentsOrderedByLastName()
    fun getStudentsOrderedByPhoneNumber() = studentDao.getStudentsOrderedByPhoneNumber()

    suspend fun deleteStudent(student: Student) {
        studentDao.deleteStudent(student)
    }

//    suspend fun searchStudents(query: String): List<Student> {
//        return studentDao.searchStudents("%$query%")
//    }





}