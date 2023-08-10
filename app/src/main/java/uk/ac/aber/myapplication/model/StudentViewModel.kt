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
        val startDate: LocalDateTime
        val endDate: LocalDateTime
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
            }
            // We can now update the state variable
            studentSearch = newStudentSearch
        }
    }
}

//    private val _sortType = MutableStateFlow(SortType.FIRST_NAME)
//    private val _students = _sortType
//        .flatMapLatest { sortType ->
//            when(sortType) {
//                SortType.FIRST_NAME -> repository.getStudentsOrderedByFirstName()
//                SortType.LAST_NAME -> repository.getStudentsOrderedByLastName()
//                SortType.PHONE_NUMBER -> repository.getStudentsOrderedByPhoneNumber()
//            }
//        }
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
//
//    private val _state = MutableStateFlow(StudentState())
//    val state = combine(_state, _sortType, _students) { state, sortType, students ->
//        state.copy(
//            students = students,
//            sortType = sortType
//        )
//    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), StudentState())
//
//    fun onEvent(event: StudentEvent) {
//        when(event) {
//            is StudentEvent.DeleteStudent -> {
//                viewModelScope.launch {
//                    repository.deleteStudent(event.student)
//                }
//            }
//            StudentEvent.HideDialog -> {
//                _state.update { it.copy(
//                    isAddingStudent = false
//                )
//
//                }
//            }
//            StudentEvent.SaveStudent -> {
//                val stateValue = state.value
//                val firstName = stateValue.firstName
//                val lastName = stateValue.lastName
//                val phoneNumber = stateValue.phoneNumber
//                val phoneNumberParent1 = stateValue.phoneNumberParent1
//                val phoneNumberParent2 = stateValue.phoneNumberParent2
//                val gender = stateValue.gender
//                val day = stateValue.day
//                val hour = stateValue.hour
//                val price = stateValue.price
//                val school = stateValue.school
//                val year = stateValue.year
//                val imagePath = stateValue.imagePath
//
//
//                if(firstName.isNotBlank() && lastName.isNotBlank() && phoneNumber.isNotBlank()) {
//                    val student = Student(
//                        firstName = firstName,
//                        lastName = lastName,
//                        phoneNumber = phoneNumber,
//                        phoneNumberParent1 = phoneNumberParent1,
//                        phoneNumberParent2 = phoneNumberParent2,
//                        gender = gender,
//                        day = day,
//                        hour = hour,
//                        debt = 0,
//                        price = price,
//                        school = school,
//                        year = year,
//                        imagePath = imagePath
//
//                    )
//                    viewModelScope.launch {
//                        repository.insertSingleStudent(student)
//                    }
//                    _state.update { it.copy(
//                        isAddingStudent = false,
//                        firstName = "",
//                        lastName = "",
//                        phoneNumber = "",
//                        phoneNumberParent1 = "",
//                        phoneNumberParent2 = "",
//                        gender = Gender.MALE,
//                        day = DayOfWeek.SUNDAY,
//                        hour = LocalTime.of(0,0),
//                        price = 0,
//                        school = "",
//                        year = 0,
//                        imagePath = "default.png"
//
//                    )}
//                }
//            }
//            is StudentEvent.SetDay -> {
//                _state.update { it.copy(
//                    day = event.day
//                ) }
//            }
//            is StudentEvent.SetDebt -> {
//                _state.update { it.copy(
//                    debt = event.debt
//                ) }
//            }
//            is StudentEvent.SetFirstName -> {
//                _state.update { it.copy(
//                    firstName = event.firstName
//                ) }
//            }
//            is StudentEvent.SetGender -> {
//                _state.update { it.copy(
//                    gender = event.gender
//                ) }
//            }
//            is StudentEvent.SetHour -> {
//                _state.update { it.copy(
//                    hour = event.hour
//                ) }
//            }
//            is StudentEvent.SetImagePath -> {
//                _state.update { it.copy(
//                    imagePath = event.imagePath
//                ) }
//            }
//            is StudentEvent.SetLastName -> {
//                _state.update { it.copy(
//                    lastName = event.lastName
//                ) }
//            }
//            is StudentEvent.SetPhoneNumber -> {
//                _state.update { it.copy(
//                    phoneNumber = event.phoneNumber
//                ) }
//            }
//            is StudentEvent.SetPhoneNumberParent1 -> {
//                _state.update { it.copy(
//                    phoneNumberParent1 = event.phoneNumberParent1
//                ) }
//            }
//            is StudentEvent.SetPhoneNumberParent2 -> {
//                _state.update { it.copy(
//                    phoneNumberParent2 = event.phoneNumberParent2
//                ) }
//            }
//            is StudentEvent.SetPrice -> {
//                _state.update { it.copy(
//                    price = event.price
//                ) }
//            }
//            is StudentEvent.SetSchool -> {
//                _state.update { it.copy(
//                    school = event.school
//                ) }
//            }
//            is StudentEvent.SetYear -> {
//                _state.update { it.copy(
//                    year = event.year
//                ) }
//            }
//            StudentEvent.ShowDialog -> {
//                _state.update { it.copy(
//                    isAddingStudent = true
//                ) }
//            }
//            is StudentEvent.SortStudent -> {
//                _state.update { it.copy(
//                    isAddingStudent = true
//                ) }
//            }
//        }
//    }
//}
