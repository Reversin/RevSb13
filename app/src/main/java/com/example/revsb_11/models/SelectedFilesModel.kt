package com.example.revsb_11.models


import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.revsb_11.dataclasses.SelectedFile
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class SelectedFilesModel(private val prefs: SharedPreferences) {

    private val gson = Gson()
    private fun saveInModel(data: List<SelectedFile>) {
        val json = gson.toJson(data)
        prefs.edit { putString(PREF_KEY_NAME, json) }
    }

    fun saveSelectedFile(
        filePath: String?,
        filePathWithName: String?,
        fileSize: String?,
        longTermPath: String,
        fileComments: String
    ) {
        val selectedFile = SelectedFile(filePath, filePathWithName, fileSize, longTermPath, fileComments)
        val existingFileName = getSelectedFiles().toMutableList()
        existingFileName.forEach { file ->
            if ((file.filePath).equals(selectedFile.filePath)) {
                existingFileName.remove(file)
                existingFileName.add(0, file)
                saveInModel(existingFileName)
                return
            }
        }
        existingFileName.add(0, selectedFile)
        saveInModel(existingFileName)
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
        saveInModel(existingFileName)
    }

    fun deleteChangedFile(uri: String, newFileComment: String) {
        val existingFileName = getSelectedFiles().toMutableList()
        val oldFileName = existingFileName.find { it.longTermPath == uri }
        existingFileName.remove(oldFileName)
        if (oldFileName != null) {
            oldFileName.fileComments = newFileComment
            existingFileName.add(0, oldFileName)
        }
        saveInModel(existingFileName)
    }


    companion object {
        private const val PREF_KEY_NAME = "1"
    }
}
