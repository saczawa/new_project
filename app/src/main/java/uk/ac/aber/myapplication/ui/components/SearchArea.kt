package uk.ac.aber.myapplication.model

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import uk.ac.aber.myapplication.R



@Composable
fun SearchArea(
    modifier: Modifier = Modifier,
    studentSearch: StudentSearch,
    genderList: List<String>,
    studyPeriodList: List<String>,
    updateSearch: (StudentSearch) -> Unit = {},
) {
    var searchText by rememberSaveable { mutableStateOf("") }

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
        Row{
            TextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                    updateSearch(
                        StudentSearch(
                            nameQuery = it,
                            gender = studentSearch.gender,
                            studyPeriod = studentSearch.studyPeriod
                        )
                    )
                },
                label = { Text(stringResource(id = R.string.search_student)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }
}
