package com.revsb_11.models


import android.content.SharedPreferences
import androidx.core.content.edit
import com.revsb_11.models.dataclasses.SelectedFile
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class SelectedFilesModel(private val prefs: SharedPreferences, private val gson: Gson) {


    private fun saveListInModel(data: List<SelectedFile>) {
        val json = gson.toJson(data)
        prefs.edit { putString(PREF_KEY_NAME, json) }
    }

    fun saveSelectedFile(
        filePath: String,
        fileSize: String,
        longTermPath: String,
        fileComments: String
    ) {
        val selectedFile = SelectedFile(filePath, fileSize, longTermPath, fileComments)
        val existingFileName = getSelectedFiles().toMutableList()
        existingFileName.forEach { file ->
            if ((file.filePath).equals(selectedFile.filePath)) {
                existingFileName.remove(file)
                existingFileName.add(0, file)
                saveListInModel(existingFileName)
                return
            }
        }
        existingFileName.add(0, selectedFile)
        saveListInModel(existingFileName)
    }


    fun getSelectedFiles(): List<SelectedFile> {
        val json = prefs.getString(PREF_KEY_NAME, null)
        val gson = Gson()
        return gson.fromJson(json, object : TypeToken<List<SelectedFile>>() {}.type)
            ?: mutableListOf()
    }

    fun deleteSelectedFile(selectedFile: SelectedFile) {
        val existingFileName = getSelectedFiles().toMutableList()
        existingFileName.remove(selectedFile)
        saveListInModel(existingFileName)
    }

    fun deleteChangedFile(uri: String, newFileComment: String) {
        val existingFileName = getSelectedFiles().toMutableList()
        val oldFileName = existingFileName.find { it.longTermPath == uri }
        existingFileName.remove(oldFileName)
        if (oldFileName != null) {
            oldFileName.fileComments = newFileComment
            existingFileName.add(0, oldFileName)
        }
        saveListInModel(existingFileName)
    }

    fun saveTransferSelectedFile(selectedFile: SelectedFile) {
        val json = gson.toJson(selectedFile)
        prefs.edit { putString(TRANSFER_FILE, json) }
    }

    fun getTransferSelectedFile(): SelectedFile? {
        val json = prefs.getString(TRANSFER_FILE, null)
        return if (json != null) gson.fromJson(json, SelectedFile::class.java) else null // Десериализация JSON обратно в объект SelectedFile
    }


    companion object {
        private const val PREF_KEY_NAME = "1"
        private const val TRANSFER_FILE = "transfer_file"
    }
}
