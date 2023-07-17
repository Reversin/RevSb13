package com.example.revsb_11.data

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentResolver.MimeTypeInfo
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import androidx.core.content.ContextCompat
import com.example.revsb_11.R
import java.io.File


class WorkingWithFiles {

    fun filePathHandlingSize(contentResolver: ContentResolver?, uri: Uri): String {
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

    fun grantUriPermissions(contentResolver: ContentResolver?, uri: Uri): Uri {
        val takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        contentResolver?.takePersistableUriPermission(uri, takeFlags)
        return uri
    }

    @SuppressLint("Range")
    fun getFileNameFromUri(contentResolver: ContentResolver, fileUri: Uri): FileName? {
        val projection =
            arrayOf(MediaStore.MediaColumns.DISPLAY_NAME, MediaStore.MediaColumns.MIME_TYPE)
        val cursor = contentResolver.query(fileUri, projection, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val fileName = it.getString(it.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME))
                val format = it.getString(it.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE))
                return FileName(fileName, format)
            }
        }
        return null
    }

}
