package uk.ac.aber.myapplication.ui.screens.allstudents

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.myapplication.ui.components.TopLevelScaffold
import uk.ac.aber.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text


@Composable
fun StudentScreen(
    navController: NavHostController
) {
    TopLevelScaffold(
        navController = navController
    )
    { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Text(
                text = "Student Screen",
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Preview
@Composable
private fun StudentScreenPreview() {
    val navController = rememberNavController()
    MyApplicationTheme(dynamicColor = false) {
        StudentScreen(navController)
    }
}
