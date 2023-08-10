package uk.ac.aber.myapplication.ui.navigation

sealed class Screen(val route: String) {
    object Student : Screen("student")
    object Plan : Screen("plan")
    object AddStudent : Screen("addStudent")
}

val screens = listOf(
    Screen.Student,
    Screen.Plan
)
