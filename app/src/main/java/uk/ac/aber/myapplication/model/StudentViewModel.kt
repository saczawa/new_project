package uk.ac.aber.myapplication.model

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import uk.ac.aber.myapplication.R
import uk.ac.aber.myapplication.datasource.MyApplicationRepository
import java.time.DayOfWeek
import java.time.LocalTime
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.time.LocalDateTime
import java.util.*

const val NUM_DAYS_RECENT: Long = 30

class StudentViewModel(application: Application): AndroidViewModel(application) {

    private val repository: MyApplicationRepository = MyApplicationRepository(application)

    var recentStudents: LiveData<List<Student>> = loadRecentStudents()
        private set

    var studentList: LiveData<List<Student>> = repository.getAllStudents()
        private set

    private val anyGender = application.resources.getStringArray(R.array.gender_array)[0]
    private val studyPeriod = application.resources.getStringArray(R.array.study_period_array)[0]

    var studentSearch: StudentSearch by mutableStateOf(
        StudentSearch(
            gender = anyGender,
            studyPeriod = studyPeriod
        )
    )
        private set

    fun updateStudentSearch(value: StudentSearch) {
        // Now reissue the search
        getStudent(value)
    }

    fun insertStudent(newStudent: Student) {
        viewModelScope.launch(Dispatchers.IO){
            repository.insert(newStudent)
        }
    }
    private fun loadRecentStudents(): LiveData<List<Student>> {
        // We actually make the present the future. This is a fudge to
        // make sure the LiveData query remains relevant to the admission
        // of new cats after the query has been made. If we don't do this
        // the LiveData will not emit onChange requests to its Observers.
        // Bug: we should force re-query when the real current date
        // changes to a new day, otherwise the recent cats period for
        // the LiveData query will stretch!
        val endDate = LocalDateTime.now().plusDays(365)
        val pastDate = LocalDateTime.now().minusDays(NUM_DAYS_RECENT)

        return repository.getRecentStudents(pastDate, endDate)
    }

    private fun getStudent(newStudentSearch: StudentSearch) {
        var changed = false

        // Did anything change since last time?
        if (newStudentSearch.gender != studentSearch.gender) {
            changed = true
        }
        if (newStudentSearch.studyPeriod != studentSearch.studyPeriod) {
            changed = true
        }

        if (changed) {
            // We load again based on the values of newCatSearch.
            // We look for values that are defaults: those are the ones that
            // need excluding from the request, and determine which method
            // Dao overload to call.
            if (newStudentSearch.gender != anyGender && newStudentSearch.studyPeriod == studyPeriod) {
                studentList = repository.getStudentsByGender(
                    Gender.valueOf(
                        newStudentSearch.gender.uppercase(Locale.ROOT)
                    )
                )
//            } else if (newStudentSearch.gender == anyGender && newStudentSearch.studyPeriod != studyPeriod) {
//                studentList = repository.searchStudentsByName(query = )
            }

            else {
                studentList = repository.getAllStudents()
            }
            // We can now update the state variable
            studentSearch = newStudentSearch
        }
    }
}
