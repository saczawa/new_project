package uk.ac.aber.myapplication.ui.screens.allstudents

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.myapplication.ui.components.TopLevelScaffold
import uk.ac.aber.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.*
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import uk.ac.aber.myapplication.ui.components.DefaultSnackbar
import uk.ac.aber.myapplication.ui.navigation.Screen
import uk.ac.aber.myapplication.R
import uk.ac.aber.myapplication.model.*
import uk.ac.aber.myapplication.ui.components.StudentCard

@Composable
fun StudentScreenTopLevel(
    navController: NavHostController,
    studentViewModel: StudentViewModel = viewModel()
) {
    val studentList by studentViewModel.studentList.observeAsState(listOf())

    StudentScreen(
        studentList = studentList,
        studentSearch = studentViewModel.studentSearch,
        updateSearchCriteria = { studentSearch ->
            studentViewModel.updateStudentSearch(studentSearch)
        },
        navController = navController
    )
}

@Composable
fun StudentScreen(
    studentList: List<Student> = listOf(),
    studentSearch: StudentSearch = StudentSearch(),
    updateSearchCriteria: (StudentSearch) -> Unit = {},
    navController: NavHostController
) {

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    TopLevelScaffold(
        navController = navController,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.AddStudent.route)
            },
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = stringResource(R.string.add_student_fab)
                )
            }
        },
        snackbarContent = { data ->
            DefaultSnackbar(
                data = data,
                modifier = Modifier.padding(bottom = 4.dp),
                onDismiss = {
                    // An opportunity to do work such as undoing the
                    // add cat operation. We'll just dismiss the snackbar
                    data.dismiss()
                }
            )
        },
        coroutineScope = coroutineScope,
        snackbarHostState = snackbarHostState
    )
    { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            val genderList = stringArrayResource(id = R.array.gender_array).toList()
            val studyPeriodList = stringArrayResource(id = R.array.study_period_array).toList()
//            val ageList = stringArrayResource(id = R.array.age_range_array).toList()

            val state = rememberLazyGridState() // new
            val context = LocalContext.current

            SearchArea(
                studentSearch = studentSearch,
                genderList = genderList,
                studyPeriodList = studyPeriodList
            ) {
                updateSearchCriteria(it)
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                state = state, // new
                contentPadding = PaddingValues(bottom = 64.dp), // Add some space at the end so that FAB not hidden
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp)
            ) {
                items(studentList) {
                    StudentCard(
                        student = it,
                        modifier = Modifier
                            .padding(end = 4.dp, top = 7.dp),
                        selectAction = { student ->
                            Toast.makeText(context, "Selected ${student.firstName}", Toast.LENGTH_LONG)
                                .show()
                        },
                        deleteAction = { student ->
                            Toast.makeText(context, "Delete ${student.firstName}", Toast.LENGTH_LONG).show()
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun StudentScreenPreview() {
    val navController = rememberNavController()
    MyApplicationTheme(dynamicColor = false) {
        StudentScreen(navController = navController)
    }
}
