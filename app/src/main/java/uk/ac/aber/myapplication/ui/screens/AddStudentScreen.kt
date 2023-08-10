package uk.ac.aber.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.R
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import java.time.LocalDate
import java.time.format.DateTimeFormatter
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AddStudentScreen(
//    navController: NavHostController,
//    insertStudent: (Student) -> Unit = {}
//) {
//    // We don't want to show them the default "any" value at the start. That way we don't
//    // need to check this when we save. copyOfRange gives us the part we're interested in
//    var values = stringArrayResource(R.array.gender_array)
//    val genderValues = values.copyOfRange(1, values.size)
//    values = stringArrayResource(R.array.breed_array)
//    val breedValues = values.copyOfRange(1, values.size)
//
//    var catName by rememberSaveable { mutableStateOf("") }
//    var gender by rememberSaveable { mutableStateOf( genderValues[0] ) }
//    var breed by rememberSaveable { mutableStateOf( breedValues[0] ) }
//    var catDescription by rememberSaveable { mutableStateOf("") }
//    val defaultImagePath = stringResource(R.string.default_image_path)
//    var imagePath by rememberSaveable { mutableStateOf(defaultImagePath) }
//    var dob by remember { mutableStateOf(LocalDate.now()) }
//
//    val formattedDob by remember {
//        derivedStateOf {
//            DateTimeFormatter
//                .ofPattern("MMM dd yyyy")
//                .format(dob)
//        }
//    }
//
//    Scaffold(
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = {
//                    insertStudent(
//                        name = catName,
//                        gender = gender,
//                        breed = breed,
//                        dob = dob,
//                        description = catDescription,
//                        imagePath = imagePath,
//                        doInsert = { newCat ->
//                            insertStudent(newCat)
//                        }
//                    )
//                    // We can now go back to caller
//                    navController.navigateUp()
//                },
//            ) {
//                Icon(
//                    imageVector = Icons.Filled.Check,
//                    contentDescription = stringResource(R.string.add_cat)
//                )
//            }
//        },
//        topBar = {
//            SmallTopAppBar(
//                title = {
//                    Text(stringResource(R.string.addCat))
//                },
//                navigationIcon = {
//                    IconButton(
//                        onClick = {
//                            navController.navigateUp()
//                        }
//                    ) {
//                        Icon(
//                            imageVector = Icons.Filled.ArrowBack,
//                            contentDescription = stringResource(R.string.goBack)
//                        )
//                    }
//                }
//            )
//        }
//    ) { innerPadding ->
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
//            modifier = Modifier
//                .padding(innerPadding)
//                .fillMaxSize()
//        ) {
//
//
//            CatImage(
//                imagePath = imagePath,
//                modifier = Modifier
//                    .padding(start = 24.dp, end = 24.dp),
//                updateImagePath = {
//                    imagePath = it
//                }
//            )
//
//            CatNameInput(
//                catName = catName,
//                modifier = Modifier
//                    .padding(top = 16.dp, start = 24.dp, end = 24.dp)
//                    .fillMaxWidth(),
//                updateName = {
//                    catName = it
//                }
//            )
//
//            GenderInput(
//                values = genderValues,
//                modifier = Modifier
//                    .padding(top = 8.dp),
//                updateGender = {
//                    gender = it
//                }
//            )
//
//            BreedInput(
//                values = breedValues,
//                modifier = Modifier
//                    .padding(top = 8.dp),
//                updateBreed = {
//                    breed = it
//                }
//            )
//
//            DateOfBirth(
//                formattedDob = formattedDob,
//                modifier = Modifier
//                    .padding(top = 8.dp),
//                updateDob = {
//                    dob = it
//                }
//            )
//
//            CatDescriptionInput(
//                catDescription = catDescription,
//                modifier = Modifier
//                    .padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 8.dp)
//                    .fillMaxWidth()
//                    .weight(1f),
//                updateDescription = {
//                    catDescription = it
//                }
//            )
//        }
//    }
//}