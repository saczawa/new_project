package uk.ac.aber.myapplication.model

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchArea(
    modifier: Modifier = Modifier,
    studentSearch: StudentSearch,
    genderList: List<String>,
    studyPeriodList: List<String>,
    updateSearch: (StudentSearch) -> Unit = {},
) {

    var dialogIsOpen by rememberSaveable { mutableStateOf(false) }

    Card(
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
    ) {
        Row {
            ButtonSpinner(
                items = studyPeriodList,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp, top = 8.dp, end = 8.dp),
                itemClick = {
                    updateSearch(
                        StudentSearch(
                            gender = studentSearch.gender,
                            studyPeriod = it
                        )
                    )
                }
            )

            ButtonSpinner(
                items = genderList,
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp, end = 8.dp),
                itemClick = {
                    updateSearch(
                        StudentSearch(

                            gender = it,
                            studyPeriod = studentSearch.studyPeriod
                        )
                    )
                }
            )
        }
    }
}
