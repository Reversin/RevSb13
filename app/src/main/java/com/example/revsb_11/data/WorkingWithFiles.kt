package com.example.revsb_11.data

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.OpenableColumns
import androidx.core.content.FileProvider
import androidx.documentfile.provider.DocumentFile
import java.io.File
import java.io.FileOutputStream


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

    fun getFileNameFromUri(contentResolver: ContentResolver, fileUri: Uri): String? {
        val cursor = contentResolver.query(fileUri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val displayNameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (displayNameIndex != -1) {
                    return it.getString(displayNameIndex)
                }
            }
        }
        return null
    }
    fun renameFile(oldFilePath: String, newFileName: String): String {
        val oldFile = File(oldFilePath)
        val newFilePath = "${oldFile.parent}/$newFileName"
        val newFile = File(newFilePath)
        return newFilePath
    }
    fun parseStringToData(string: String): Data {
        val parts = string.split("filePath","fileSize","fullPath")
        val filePath = parts[0].trim()
        val fileSize = parts[1].trim()
        val fullPath = parts[2].trim()
        return Data(filePath, fileSize, fullPath)


    }

}


