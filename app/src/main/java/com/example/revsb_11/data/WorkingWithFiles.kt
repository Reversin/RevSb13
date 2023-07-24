package com.example.revsb_11.data

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns


class WorkingWithFiles(val contentResolver: ContentResolver?) {
    fun filePathHandlingSize(uri: Uri): String {
        var fileSize: String? = null
        val cursor = contentResolver
            ?.query(uri, null, null, null, null)
        cursor.use {
            if (it != null) {
                if (it.moveToFirst()) {
                    val size = it.getColumnIndex(OpenableColumns.SIZE)
                    fileSize = formatFileSize(it.getLong(size))
                }
            }
            return fileSize.toString()
        }
    }

    private fun formatFileSize(fileSizeBytes: Long): String {
        val units = arrayOf("B", "KB", "MB", "GB", "TB")

        var fileSize = fileSizeBytes.toDouble()
        var unitIndex = 0

        for (i in 0 until units.size - 1) {
            if (fileSize < 1024) {
                break
            }
            fileSize /= 1024
            unitIndex++
        }
        return "%.2f %s".format(fileSize, units[unitIndex])
    }

    fun grantUriPermissions(uri: Uri): Uri {
        val takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        contentResolver?.takePersistableUriPermission(uri, takeFlags)
        return uri
    }

    @SuppressLint("Range")
    fun getFileNameFromUri(fileUri: Uri): String? {
        val projection =
            arrayOf(MediaStore.MediaColumns.DISPLAY_NAME, MediaStore.MediaColumns.MIME_TYPE)
        val cursor = contentResolver?.query(fileUri, projection, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                return it.getString(it.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME))
            }
        }
        return null
    }
    fun getBitmapImageFromUri(fileUri: Uri): Bitmap =
        contentResolver?.openInputStream(fileUri).use { inputStream ->
            BitmapFactory.decodeStream(inputStream)
        }
}
