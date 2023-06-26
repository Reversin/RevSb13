package com.example.revsb_11.data

import android.content.ContentResolver
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.support.v4.os.IResultReceiver._Parcel
import com.example.revsb_11.contracts.FirstFragmentContract


class GetNameFromUri {

    fun recyclePath(contentResolver: ContentResolver?, uri: Uri): Data {

        var fileName: String? = null
        var fileSize: String? = null
        val cursor = contentResolver
            ?.query(uri, null, null, null, null)
        cursor.use {
            if (it != null) {
                if (it.moveToFirst()) {
                    val displayName = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    val size = it.getColumnIndex(OpenableColumns.SIZE)
                    fileName = it.getString(displayName)
                    fileSize = formatFileSize(it.getLong(size))
                }
            }
            return Data(fileName, fileSize.toString())
        }
    }

    fun formatFileSize(fileSizeBytes: Long): String {
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

}


