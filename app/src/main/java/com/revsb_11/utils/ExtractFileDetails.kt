package com.revsb_11.utils

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import androidx.core.net.toUri


class ExtractFileDetails(private val contentResolver: ContentResolver?) {
    fun getFileSize(uri: Uri): String {
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
            if (fileSize < MIN_FILE_SIZE) {
                break
            }
            fileSize /= MIN_FILE_SIZE
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
    fun getFileNameFromUri(fileUri: String): String? {
        val projection =
            arrayOf(MediaStore.MediaColumns.DISPLAY_NAME, MediaStore.MediaColumns.MIME_TYPE)
        val cursor = contentResolver?.query(fileUri.toUri(), projection, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                return it.getString(it.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME))
            }
        }
        return null
    }
    @SuppressLint("Range")
    fun getFileFormatFromUri(fileUri: String): String? {
        val projection =
            arrayOf(MediaStore.MediaColumns.DISPLAY_NAME, MediaStore.MediaColumns.MIME_TYPE)
        val cursor = contentResolver?.query(fileUri.toUri(), projection, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                return it.getString(it.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE))
            }
        }
        return null
    }
    fun getBitmapImageFromUri(fileUri: String): Bitmap =
        contentResolver?.openInputStream(fileUri.toUri()).use { inputStream ->
            BitmapFactory.decodeStream(inputStream)
        }

    companion object {
        const val MIN_FILE_SIZE = 1024
        const val TRANSFER_FILE_URI = "transfer_file_uri"
        const val TRANSFER_FILE_COMMENT = "transfer_file_comment"
    }

}
