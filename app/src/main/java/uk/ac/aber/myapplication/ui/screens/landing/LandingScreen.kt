package uk.ac.aber.myapplication.ui.screens
//
//import androidx.appcompat.app.AppCompatDelegate
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.unit.dp
//import androidx.core.os.LocaleListCompat
//import androidx.navigation.NavController
//import uk.ac.aber.myapplication.R
//import uk.ac.aber.myapplication.model.ButtonSpinner
//import uk.ac.aber.myapplication.ui.navigation.Screen
//import uk.ac.aber.myapplication.ui.theme.md_theme_light_primary
//
//@Composable
//fun LandingScreen(
//    navController: NavController
//) {
//    Column(
//        modifier = Modifier
//            .padding(8.dp)
//            .fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally,
//    ) {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.Center
//        ) {
//            Text(
//                text = stringResource(R.string.choose_role_btn),
//            )
//        }
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.Center
//        ) {
//            RoleButton(stringResource(R.string.teacher_btn), navController, Screen.Plan)
//        }
//
//        Row(
//            modifier = Modifier
//                .padding(12.dp)
//                .fillMaxWidth(),
//            horizontalArrangement = Arrangement.Center
//        ) {
//            RoleButton(stringResource(R.string.student_btn), navController, Screen.Student)
//        }
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.Center
//        ) {
//            LanguageSelector()
//        }
//    }
//}
//
//@Composable
//fun RoleButton(text: String, navController: NavController, target: Screen) {
//    Button(
//        shape = MaterialTheme.shapes.medium,
//        colors = ButtonDefaults.buttonColors(containerColor = md_theme_light_primary),
//        modifier = Modifier.padding(10.dp).width(200.dp),
//        onClick = {
//            navController.navigate(target.route)
//        }
//    ) {
//        Text(text = text)
//    }
//}
//
//@Composable
//fun LanguageSelector() {
//    val languageOptions = mapOf(
//        R.string.lang_en to "en",
//        R.string.lang_pl to "pl"
//    ).mapKeys { stringResource(it.key) }
//
//    ButtonSpinner(
//        items = languageOptions.keys.toList(),
//        itemClick = {
//            changeLocale(languageOptions[it])
//        }
//    )
//}
//
//fun changeLocale(tag: String?) {
//    val locale = LocaleListCompat.forLanguageTags(tag)
//
//    AppCompatDelegate.setApplicationLocales(locale)
//}
//
