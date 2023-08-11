package uk.ac.aber.myapplication.ui.screens.addStudent

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import uk.ac.aber.myapplication.R
import uk.ac.aber.myapplication.model.ButtonSpinner
import uk.ac.aber.myapplication.model.Gender
import uk.ac.aber.myapplication.model.Student
import uk.ac.aber.myapplication.model.StudentViewModel
import uk.ac.aber.myapplication.model.util.ResourceUtil
import java.io.File
import java.io.IOException
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


@Composable
fun AddStudentScreenTopLevel(
    navController: NavHostController,
    studentViewModel: StudentViewModel = viewModel()
) {
    AddStudentScreen(
        navController = navController,
        insertStudent = { newStudent ->
            studentViewModel.insertStudent(newStudent)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddStudentScreen(
    navController: NavHostController,
    insertStudent: (Student) -> Unit = {}
) {

    var values = stringArrayResource(R.array.gender_array)
    val genderValues = values.copyOfRange(1, values.size)
    values = stringArrayResource(R.array.day_array)
    val dayValue = values.copyOfRange(1, values.size)
    val hourValues = generateHourValues()
    values = stringArrayResource(R.array.school_types_array)
    val schoolTypesValue = values.copyOfRange(1, values.size)
    values = stringArrayResource(R.array.class_array)
    val classTypesValue = values.copyOfRange(1, values.size)


    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var phoneNumberParent1 by rememberSaveable { mutableStateOf("") }
    var phoneNumberParent2 by rememberSaveable { mutableStateOf("") }
    var gender by rememberSaveable { mutableStateOf( genderValues[0] ) }
    var day by rememberSaveable { mutableStateOf(DayOfWeek.SUNDAY) }
    var hour by rememberSaveable { mutableStateOf(LocalTime.of(10,0)) }
    var debt by rememberSaveable { mutableStateOf(0) } // Assuming default is 0
    var price by rememberSaveable { mutableStateOf(0) } // Assuming default is 0
    var school by rememberSaveable { mutableStateOf("") }
    var year by rememberSaveable { mutableStateOf(0) } // Assuming default year is 0 (You may want to adjust this)
    var howLongStudent by rememberSaveable { mutableStateOf(LocalDateTime.now()) }
    var startDate by rememberSaveable { mutableStateOf(LocalDateTime.now()) }
    val defaultImagePath = stringResource(R.string.default_image_path)
    var imagePath by rememberSaveable { mutableStateOf(defaultImagePath) }

    var dob by remember { mutableStateOf(LocalDate.now()) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    insertStudent(
                        firstName = firstName,
                        lastName = lastName,
                        phoneNumber = phoneNumber,
                        phoneNumberParent1 = phoneNumberParent1,
                        phoneNumberParent2 = phoneNumberParent2,
                        gender = gender,
                        day = day,
                        hour = hour,
                        debt = debt,
                        price = price,
                        school = school,
                        year = year,
                        howLongStudent = howLongStudent,
                        startDate = startDate,
                        imagePath = imagePath,
                        doInsert = { newStudent ->
                            insertStudent(newStudent)
                        }
                    )
                    // We can now go back to caller
                    navController.navigateUp()
                },
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = stringResource(R.string.add_student)
                )
            }
        },
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(stringResource(R.string.addStudent))
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.goBack)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
                 modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            StudentImage(
                imagePath = imagePath,
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp),
                updateImagePath = {
                    imagePath = it
                }
            )

            FirstNameInput(
                firstName = firstName,
                modifier = Modifier
                    .padding(top = 8.dp, start = 24.dp, end = 24.dp)
                    .fillMaxWidth(),
                updateName = {
                    firstName = it
                }
            )

            LastNameInput(
                lastName = lastName,
                modifier = Modifier
                    .padding(top = 8.dp, start = 24.dp, end = 24.dp)
                    .fillMaxWidth(),
                updateLastName = {
                    lastName = it
                }
            )

            PhoneNumberInput(
                phone = phoneNumber,
                modifier = Modifier
                    .padding(top = 8.dp, start = 24.dp, end = 24.dp)
                    .fillMaxWidth(),
                updatePhoneNumber = {
                    phoneNumber = it
                }
            )

            PhoneNumberParent1Input(
                phone = phoneNumberParent1,
                modifier = Modifier
                    .padding(top = 8.dp, start = 24.dp, end = 24.dp)
                    .fillMaxWidth(),
                updatePhoneNumberParent1 = {
                    phoneNumberParent1 = it
                }
            )

            PhoneNumberParent2Input(
                phone = phoneNumberParent2,
                modifier = Modifier
                    .padding(top = 8.dp, start = 24.dp, end = 24.dp)
                    .fillMaxWidth(),
                updatePhoneNumberParent2 = {
                    phoneNumberParent2 = it
                }
            )


            GenderInput(
                values = genderValues,
                modifier = Modifier
                    .padding(top = 8.dp, start = 24.dp, end = 24.dp),
                updateGender = {
                    gender = it
                }
            )

            DayOfWeekInput(
                values = dayValue,
                modifier = Modifier
                    .padding(top = 8.dp),
                updateDay = {
                    day = it
                }
            )

            HourInput(
                values = hourValues,
                modifier = Modifier
                    .padding(top = 8.dp),
                updateHour = {
                    hour = it
                }
            )

            SchoolInput(
                school = schoolTypesValue,
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 8.dp)
                    .fillMaxWidth()
                    .weight(1f),
                updateSchool = {
                    school = it
                }
            )

            YearInput(
                year = classTypesValue, // Zmieniłem nazwę parametru na 'yearValues'
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                updateYear = {
                    try {
                        year = it.toInt() // Przekształć wartość na Int
                    } catch (e: NumberFormatException) {
                        // Jeśli konwersja się nie powiedzie, zignoruj lub obsłuż błąd
                    }
                }
            )

            PriceInput(
                price = price,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                updatePrice = {
                    price = it
                }
            )

        }
    }
}

private fun insertStudent(
    firstName: String,
    lastName: String,
    phoneNumber: String,
    phoneNumberParent1: String,
    phoneNumberParent2: String,
    gender: String,
    day: DayOfWeek,
    hour: LocalTime,
    debt: Int,
    price: Int,
    school: String,
    year: Int,
    howLongStudent: LocalDateTime,
    startDate: LocalDateTime,
    imagePath: String,
    doInsert: (Student) -> Unit = {}
) {
    val newStudent = Student(
        firstName = firstName,
        lastName = lastName,
        phoneNumber = phoneNumber,
        phoneNumberParent1 = phoneNumberParent1,
        phoneNumberParent2 = phoneNumberParent2,
        gender = Gender.valueOf(gender.uppercase()),
        day = day,
        hour = hour,
        debt = debt,
        price = price,
        school = school,
        year = year,
        howLongStudent = howLongStudent,
        startDate = startDate,
        imagePath = imagePath
    )

    doInsert(newStudent)
}

@Composable
fun FirstNameInput(
    firstName: String,
    modifier: Modifier,
    updateName: (String) -> Unit
) {
    OutlinedTextField(
        value = firstName,
        label = {
            Text(text = stringResource(id = R.string.student_first_name))
        },
        onValueChange = { updateName(it) },
        modifier = modifier
    )
}

@Composable
fun LastNameInput(
    lastName: String,
    modifier: Modifier,
    updateLastName: (String) -> Unit
) {
    OutlinedTextField(
        value = lastName,
        label = {
            Text(text = stringResource(id = R.string.student_last_name))
        },
        onValueChange = { updateLastName(it) },
        modifier = modifier
    )
}

@Composable
fun PhoneNumberInput(
    phone: String,
    modifier: Modifier,
    updatePhoneNumber: (String) -> Unit
) {
    OutlinedTextField(
        value = phone,
        label = {
            Text(text = stringResource(id = R.string.student_phone_number))
        },
        onValueChange = { updatePhoneNumber(it) },
        modifier = modifier
    )
}

@Composable
fun PhoneNumberParent1Input(
    phone: String,
    modifier: Modifier,
    updatePhoneNumberParent1: (String) -> Unit
) {
    OutlinedTextField(
        value = phone,
        label = {
            Text(text = stringResource(id = R.string.student_phone_number_parent1))
        },
        onValueChange = { updatePhoneNumberParent1(it) },
        modifier = modifier
    )
}

@Composable
fun PhoneNumberParent2Input(
    phone: String,
    modifier: Modifier,
    updatePhoneNumberParent2: (String) -> Unit
) {
    OutlinedTextField(
        value = phone,
        label = {
            Text(text = stringResource(id = R.string.student_phone_number_parent2))
        },
        onValueChange = { updatePhoneNumberParent2(it) },
        modifier = modifier
    )
}

@Composable
fun GenderInput(
    values: Array<String>,
    modifier: Modifier,
    updateGender: (String) -> Unit
) {
    ButtonSpinner(
        items = values.asList(),
        modifier = modifier,
        itemClick = {
            updateGender(it)
        }
    )
}

@Composable
fun DayOfWeekInput(
    values: Array<String>,
    modifier: Modifier,
    updateDay: (DayOfWeek) -> Unit
) {
    ButtonSpinner(
        items = values.asList(),
        modifier = modifier,
        itemClick = { selectedString ->
            val selectedDay = stringToDayOfWeek(selectedString)
            updateDay(selectedDay)
        }
    )
}


@Composable
fun HourInput(
    values: Array<String>,
    modifier: Modifier,
    updateHour: (LocalTime) -> Unit
) {
    ButtonSpinner(
        items = values.asList(),
        modifier = modifier,
        itemClick = { selectedString ->
            val selectedTime = stringToLocalTime(selectedString)
            updateHour(selectedTime)
        }
    )
}

@Composable
fun SchoolInput(
    school: Array<String>,
    modifier: Modifier,
    updateSchool: (String) -> Unit
) {
    ButtonSpinner(
        items = school.asList(),
        modifier = modifier,
        itemClick = {
            updateSchool(it)
        }
    )
}

@Composable
fun YearInput(
    year: Array<String>,
    modifier: Modifier,
    updateYear: (String) -> Unit
) {
    ButtonSpinner(
        items = year.asList(),
        modifier = modifier,
        itemClick = {
            updateYear(it)
        }
    )
}

@Composable
fun PriceInput(
    price: Int,
    modifier: Modifier,
    updatePrice: (Int) -> Unit
) {
    val currencySymbol = stringResource(id = R.string.currency_symbol)
    val priceWithSymbol = "${price}$currencySymbol"

    OutlinedTextField(
        value = priceWithSymbol,
        label = {
            Text(text = stringResource(id = R.string.price_label))
        },
        onValueChange = { input ->
            val strippedInput = input.filter { it.isDigit() }
            if (strippedInput.isDigitsOnly()) {
                updatePrice(strippedInput.toInt())
            }
        },
        modifier = modifier,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        readOnly = true, // prevents user from editing the currency symbol
        trailingIcon = {
            Text(currencySymbol) // This will display the currency symbol at the end of the text field
        }
    )
}


fun stringToDayOfWeek(day: String): DayOfWeek {
    return when(day) {
        "Monday" -> DayOfWeek.MONDAY
        "Tuesday" -> DayOfWeek.TUESDAY
        "Wednesday" -> DayOfWeek.WEDNESDAY
        "Thursday" -> DayOfWeek.THURSDAY
        "Friday" -> DayOfWeek.FRIDAY
        "Saturday" -> DayOfWeek.SATURDAY
        "Sunday" -> DayOfWeek.SUNDAY
        else -> throw IllegalArgumentException("Unknown day string: $day")
    }
}

fun stringToLocalTime(time: String): LocalTime {
    return LocalTime.parse(time)
}

fun generateHourValues(): Array<String> {
    val timeValues = mutableListOf<String>()
    for (hour in 0..23) {
        for (minute in arrayOf(0, 30)) {
            val formattedTime = String.format("%02d:%02d", hour, minute)
            timeValues.add(formattedTime)
        }
    }
    return timeValues.toTypedArray()
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun StudentImage(
    imagePath: String,
    modifier: Modifier,
    updateImagePath: (String) -> Unit = {}
) {
    var photoFile: File? = remember { null }
    val ctx = LocalContext.current


    val resultLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                updateImagePath(
                    "file://${photoFile!!.absolutePath}"
                )
            }
        }

    // Should recompose if imagePath changes as a result of taking the picture
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {

        GlideImage(
            model = Uri.parse(imagePath),
            contentDescription = stringResource(R.string.cat_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(200.dp)
                .clickable {
                    takePicture(
                        ctx = ctx,
                        resultLauncher = resultLauncher,
                    ) {
                        photoFile = it
                    }
                }
        )
        Text(text = stringResource(id = R.string.enterImageMsg))
    }

}

private fun takePicture(
    ctx: Context,
    resultLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>,
    updateFile: (File) -> Unit
) {
    // Code obtained and adapted from: https://developer.android.com/training/camera/photobasics
    // See configuration instructions added to AndroidManifest.xml
    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    var photoFile: File? = null

    // Create the File where the photo should go
    try {
        photoFile = ResourceUtil.createImageFile(ctx)
    } catch (ex: IOException) {
        // Error occurred while creating the File
        Toast.makeText(
            ctx,
            ctx.getString(R.string.cannot_create_image_file),
            Toast.LENGTH_SHORT
        ).show()
    }

    // Continue only if the File was successfully created
    photoFile?.let {
        val photoUri = FileProvider.getUriForFile(
            ctx,
            ctx.packageName,
            it
        )
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        // Request will fail if a camera app not available.
        // This used to use takePictureIntent.resolveActivity(requireActivity().packageManager)
        // However this requires a <query> element to be added to the manifest for
        // Android 30+. The following is simpler.
        try {
            resultLauncher.launch(takePictureIntent)
            updateFile(photoFile)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(ctx, R.string.cannotTakePicture, Toast.LENGTH_LONG)
                .show()
        }
    }

}