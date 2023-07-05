package com.example.revsb_11.data

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns


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

    fun filePathHandlingName(contentResolver: ContentResolver?, uri: Uri): String {
        var fileName: String? = null
        val cursor = contentResolver
            ?.query(uri, null, null, null, null)
        cursor.use {
            if (it != null) {
                if (it.moveToFirst()) {
                    val displayName = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    fileName = it.getString(displayName)
                }
            }
            return fileName.toString()
        }
    }

}


