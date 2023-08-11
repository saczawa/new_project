package uk.ac.aber.myapplication.model.util

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


private const val MyApp_TAG = "MyApplication"

object ResourceUtil {

    fun getPhotoFileUri(context: Context, fileName: String): File {
        val mediaStorageDir: File =
            File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), MyApp_TAG)

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(MyApp_TAG, "failed to create directory")
        }

        // Return the file target for the photo based on filename
        return File(mediaStorageDir.path + File.separator + fileName)
    }

    fun createImageFile(context: Context): File {
        // Code obtained and adapted from: https://developer.android.com/training/camera/photobasics
        // Create an image file name

        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",  /* suffix */
            storageDir /* directory */
        )
    }

}