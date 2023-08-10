package uk.ac.aber.myapplication.ui.components

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun StudentCard(
    modifier: Modifier = Modifier,
    student: Student,
    selectAction: (Student) -> Unit = {},
    deleteAction: (Student) -> Unit = {}
){
    Card(
        modifier = modifier
            .fillMaxSize()
    ) {

        ConstraintLayout {
            val (imageRef, nameRef, deleteRef) = createRefs()

            // There is a more efficient way to use Glide in LazyLists
            // The problem is that we are using a LazyVerticalGrid in the
            // caller which is incompatible with the more efficient version.
            // See https://bumptech.github.io/glide/int/compose.html
            GlideImage(
                //model = Uri.parse("file:///android_asset/images/${cat.imagePath}"),
                model = Uri.parse(student.imagePath),
                contentDescription = stringResource(R.string.student_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(190.dp)
                    .padding(top = 4.dp, start = 4.dp, end = 4.dp)
                    .constrainAs(imageRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .clickable { selectAction(student) }
            )

            Text(
                text = student.firstName,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .constrainAs(nameRef) {
                        start.linkTo(parent.start)
                        top.linkTo(imageRef.bottom)
                        bottom.linkTo(parent.bottom)
                    }
            )

            IconButton(
                onClick = { deleteAction(student) },
                modifier = Modifier.constrainAs(deleteRef) {
                    end.linkTo(parent.end)
                    top.linkTo(imageRef.bottom)
                    bottom.linkTo(parent.bottom)
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