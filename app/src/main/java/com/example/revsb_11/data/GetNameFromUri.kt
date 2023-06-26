package com.example.revsb_11.data

import android.content.ContentResolver
import android.net.Uri
import android.provider.MediaStore
import com.example.revsb_11.contracts.FirstFragmentContract


class GetNameFromUri   {

    fun recyclePath(contentResolver: ContentResolver?, uri: Uri): String? {
        val projection = arrayOf(MediaStore.MediaColumns.DATA)
        val resData = contentResolver
            ?.query(uri, projection, null, null, null)
            ?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val columnIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DATA)
                    return cursor.getString(columnIndex)
//                    val pathIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
//                    val fileName = cursor.getString(pathIndex)
//                    val file = selectedUri.path?.let(::File)
//                    val fullPath = file?.absolutePath
//                    return "$fullPath"
                }
                return null
            }
        return resData
    }


}