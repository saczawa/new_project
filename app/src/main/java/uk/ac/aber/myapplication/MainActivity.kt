package uk.ac.aber.myapplication

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import uk.ac.aber.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.myapplication.model.StudentViewModel
import uk.ac.aber.myapplication.ui.navigation.Screen
//import uk.ac.aber.myapplication.ui.screens.AddStudentScreen
import uk.ac.aber.myapplication.ui.screens.allstudents.StudentScreen
import uk.ac.aber.myapplication.ui.screens.allstudents.StudentScreenTopLevel
import uk.ac.aber.myapplication.ui.screens.plan.PlanScreen
import uk.ac.aber.myapplication.ui.screens.plan.PlanScreenTopLevel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme(dynamicColor = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BuildNavigationGraph()
                }
            }
        }
    }
}

    @Composable
    private fun BuildNavigationGraph(
        studentViewModel: StudentViewModel = viewModel()
    ) {
        // The NavController is in a place where all
        // our composables can access it.
        val navController = rememberNavController()
        var startDestination = remember { Screen.Plan.route }

        // Each NavController is associated with a NavHost.
        // This links the NavController with a navigation graph.
        // As we navigate between composables the content of
        // the NavHost is automatically recomposed.
        // Each composable destination in the graph is associated with a route.
        NavHost(
            navController = navController,
            startDestination = Screen.Plan.route
        ) {
            composable(Screen.Student.route) { StudentScreenTopLevel(navController, studentViewModel) }
            composable(Screen.Plan.route) { PlanScreenTopLevel(navController, studentViewModel) }
//            composable(Screen.AddStudent.route) { AddStudentScreen(navController) }

        }
    }