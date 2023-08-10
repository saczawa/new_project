package uk.ac.aber.myapplication.ui.screens.plan

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.myapplication.model.Student
import uk.ac.aber.myapplication.model.StudentViewModel
import uk.ac.aber.myapplication.ui.components.TopLevelScaffold
import uk.ac.aber.myapplication.ui.theme.MyApplicationTheme


@Composable
fun PlanScreenTopLevel(
    navController: NavHostController,
    catsViewModel: StudentViewModel = viewModel()
)
{
    val recentCats by catsViewModel.recentStudents.observeAsState(listOf())

    PlanScreen(
        navController = navController,
        recentStudent = recentCats
    )
}

@Composable
fun PlanScreen(
    navController: NavHostController,
    recentStudent: List<Student>) {

    val coroutineScope = rememberCoroutineScope()

    TopLevelScaffold(
        navController = navController,
        coroutineScope = coroutineScope,
    ) { innerPadding ->
        Surface(
            modifier = androidx.compose.ui.Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            PlanScreenContent(
                modifier = Modifier.padding(8.dp),
                recentStudent
            )
        }
    }
}
@Composable
private fun PlanScreenContent(
    modifier: Modifier = Modifier,
    recentCats: List<Student>
) {

}
@Preview
@Composable
private fun HomeScreenPreview(){
    val navController = rememberNavController()
    MyApplicationTheme(dynamicColor = false) {
        PlanScreen(navController, listOf())
    }
}