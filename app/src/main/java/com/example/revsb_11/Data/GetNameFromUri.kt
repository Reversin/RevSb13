package com.example.revsb_11.Data

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import com.example.revsb_11.Contracts.FirstFragmentContract
import java.io.File


class GetNameFromUri : FirstFragmentContract.Data {

    override fun recyclePath(path: ContentResolver?, selectedUri: Uri): String? {

        val resPath = path?.query(selectedUri, null, null, null, null)
            ?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val pathIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    val fileName = cursor.getString(pathIndex)
                    val file = selectedUri.path?.let(::File)
                    val fullPath = file?.absolutePath
                    return "$fullPath/$fileName"
                }
                return null
            }
        return resPath
    }


}