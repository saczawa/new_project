package uk.ac.aber.myapplication.ui.screens.plan

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.myapplication.ui.components.TopLevelScaffold
import uk.ac.aber.myapplication.ui.theme.MyApplicationTheme

@Composable
fun PlanScreen(navController: NavHostController) {

    TopLevelScaffold(
        navController = navController
    ) { innerPadding ->
        Surface(
            modifier = androidx.compose.ui.Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Text(text = "Home Screen",
                modifier = Modifier.padding(start = 8.dp))
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview(){
    val navController = rememberNavController()
    MyApplicationTheme(dynamicColor = false) {
        PlanScreen(navController)
    }
}