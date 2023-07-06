package com.example.revsb_11.data

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap


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

    @SuppressLint("Range")
    fun filePathHandlingName(contentResolver: ContentResolver?, uri: Uri): String {
        var fileName: String? = null
        val collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        val projection = arrayOf(MediaStore.MediaColumns.DISPLAY_NAME)

        val cursor = contentResolver
            ?.query(uri, projection, null, null, null)
        cursor.use {
            if (it != null) {
                if (it.moveToFirst()) {
                    fileName = it.getString(it.getColumnIndex((MediaStore.MediaColumns.DISPLAY_NAME)))
                }
            }
            return fileName.toString()
        }
    }
    @SuppressLint("Range")
    fun getLongTermFileUri(contentResolver: ContentResolver?, fileUri: Uri): Uri? {

        val cursor = contentResolver?.query(fileUri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))

                // Создаем запись в MediaStore
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, displayName)
                    put(MediaStore.MediaColumns.RELATIVE_PATH, "Documents") // Указываем путь к файлу внутри папки "Documents"
                }

                // Вставляем запись в MediaStore
                val contentUri =
                    MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)

                val insertedUri = contentResolver.insert(contentUri, contentValues)

                // Копируем содержимое файла в созданную запись
                insertedUri?.let { destinationUri ->
                    contentResolver.openOutputStream(destinationUri)?.use { outputStream ->
                        contentResolver.openInputStream(fileUri)?.use { inputStream ->
                            inputStream.copyTo(outputStream)
                        }
                    }
                }

                return insertedUri
            }
        }

        return null
    }
    fun getFileNameFromUri(contentResolver: ContentResolver, fileUri: Uri): String? {
        val cursor = contentResolver.query(fileUri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val displayNameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (displayNameIndex != -1) {
                    val displayName = it.getString(displayNameIndex)
                    return displayName
                }
            }
        }
        return null
    }

}


