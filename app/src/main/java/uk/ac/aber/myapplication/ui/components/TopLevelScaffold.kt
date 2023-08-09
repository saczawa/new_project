package uk.ac.aber.myapplication.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopLevelScaffold(
    navController: NavHostController,
    pageContent: @Composable (innerPadding: PaddingValues) -> Unit = {}
) {
    Scaffold(
        topBar = {
            MainPageTopAppBar()
        },
        bottomBar = {
            MainPageNavigationBar(navController)
        },
        content = { innerPadding ->
            pageContent(innerPadding)
        }
    )
}