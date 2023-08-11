package uk.ac.aber.myapplication.ui.components

import android.content.ClipData
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import uk.ac.aber.myapplication.model.Student
import uk.ac.aber.myapplication.R
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import android.content.ClipboardManager
import uk.ac.aber.myapplication.ui.theme.md_theme_light_surfaceTint

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun StudentCard(
    modifier: Modifier = Modifier,
    student: Student,
    selectAction: (Student) -> Unit = {},
    deleteAction: (Student) -> Unit = {}
){
    val context = LocalContext.current
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    val schoolLabel = stringResource(id = R.string.label_school)
    val yearLabel = stringResource(id = R.string.label_year)
    val priceLabel = stringResource(id = R.string.label_price)

    Card(
        modifier = modifier
            .fillMaxSize()
    ) {

        ConstraintLayout {
            val (imageRef, firstNameRef, lastNameRef,
                phoneNumberRef, dayRef, hourRef,
                schoolRef, yearRef, priceRef, deleteRef, plainRef) = createRefs()

            GlideImage(
                model = Uri.parse(student.imagePath),
                contentDescription = stringResource(R.string.student_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(150.dp)
                    .width(150.dp)
                    .padding(top = 4.dp, start = 4.dp)
                    .constrainAs(imageRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                    .clickable { selectAction(student) }
            )

            Text(
                text = student.firstName,
                fontSize = 22.sp,
                color = md_theme_light_surfaceTint,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .constrainAs(firstNameRef) {
                        start.linkTo(imageRef.end)
                        top.linkTo(parent.top)
                    }
            )

            Text(
                text = student.lastName,
                fontSize = 22.sp,
                color = md_theme_light_surfaceTint,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .constrainAs(lastNameRef) {
                        start.linkTo(firstNameRef.end, margin = 8.dp)
                        top.linkTo(parent.top)
                    }
            )

            Text(
                text = student.phoneNumber,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable {
                        // Kopiowanie numeru telefonu do schowka
                        val clip = ClipData.newPlainText("phoneNumber", student.phoneNumber)
                        clipboardManager.setPrimaryClip(clip)
                        // Opcjonalnie: możesz wyświetlić powiadomienie dla użytkownika, że numer został skopiowany
                    }
                    .constrainAs(phoneNumberRef) {
                        start.linkTo(imageRef.end)
                        top.linkTo(firstNameRef.bottom)
                    }
            )

            Text(
                text = student.day.name,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .constrainAs(dayRef) {
                        start.linkTo(imageRef.end)
                        top.linkTo(phoneNumberRef.bottom)
                    }
            )

            Text(
                text = student.hour.toString(),
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .constrainAs(hourRef) {
                        start.linkTo(dayRef.end, margin = 8.dp)
                        top.linkTo(phoneNumberRef.bottom)
                    }
            )

            Text(
                text = "${schoolLabel}: ${student.school}",
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp)
                    .constrainAs(schoolRef) {
                        start.linkTo(imageRef.end)
                        top.linkTo(dayRef.bottom)
                    }
            )

            Text(
                text = "${yearLabel}: ${student.year.toString()}",
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .constrainAs(yearRef) {
                        start.linkTo(imageRef.end)
                        top.linkTo(schoolRef.bottom)
                    }
            )

            Text(
                text = "${priceLabel}: ${student.price} £",
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .constrainAs(priceRef) {
                        start.linkTo(imageRef.end)
                        top.linkTo(yearRef.bottom)
                    }
            )

            IconButton(
                onClick = { deleteAction(student) },
                modifier = Modifier.constrainAs(deleteRef) {
                    end.linkTo(parent.end, margin = 2.dp)
                    bottom.linkTo(priceRef.bottom)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    tint = MaterialTheme.colorScheme.error,
                    contentDescription = stringResource(R.string.remove_student)
                )
            }
        }

    }
}