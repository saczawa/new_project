package uk.ac.aber.myapplication.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.myapplication.R
import uk.ac.aber.myapplication.ui.navigation.Screen
import uk.ac.aber.myapplication.ui.navigation.screens
import uk.ac.aber.myapplication.ui.theme.MyApplicationTheme

@Composable
fun MainPageNavigationBar(
    navController: NavController,
) {
    val icons = mapOf(
        Screen.Student to IconGroup(
            filledIcon = Icons.Filled.List,
            outlineIcon = Icons.Outlined.List,
            label = stringResource(id = R.string.bottom_nav_student)
        ),
        Screen.Plan to IconGroup(
            filledIcon = Icons.Filled.Receipt,
            outlineIcon = Icons.Outlined.Receipt,
            label = stringResource(id = R.string.bottom_nav_plan)
        )
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        screens.forEach { screen ->
            val isSelected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
            val labelText = icons[screen]!!.label
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = (if (isSelected)
                            icons[screen]!!.filledIcon
                        else
                            icons[screen]!!.outlineIcon),
                        contentDescription = labelText
                    )
                },
                label = { Text(labelText) },
                selected = isSelected,
                onClick = {
                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Preview
@Composable
private fun MainPageNavigationBarPreview() {
    val navController = rememberNavController()
    MyApplicationTheme(dynamicColor = false) {
        MainPageNavigationBar(navController)
    }
}