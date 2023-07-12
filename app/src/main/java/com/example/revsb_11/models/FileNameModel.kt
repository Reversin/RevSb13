package com.example.revsb_11.models


import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.revsb_11.contracts.SelectedFilesContract
import com.example.revsb_11.data.SelectedFile
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class FileNameModel(private val prefs: SharedPreferences) : SelectedFilesContract.Model {

    private val gson = Gson()
    override fun saveInModel(data: List<SelectedFile>) {
        val json = gson.toJson(data)
        prefs.edit { putString(PREF_KEY_NAME, json) }
    }

    override fun saveItem(selectedFile: SelectedFile) {
        val existingFileName = getItems().toMutableList()
        existingFileName.removeAll { (it.filePath)?.equals(selectedFile.filePath) ?: false }
        existingFileName.add(0, selectedFile)
        saveInModel(existingFileName)
    }


    override fun getItems(): List<SelectedFile> {
        val json = prefs.getString(PREF_KEY_NAME, null)
        val gson = Gson()
        return gson.fromJson(json, object : TypeToken<List<SelectedFile>>() {}.type) ?: mutableListOf()
    }

    override fun deleteItem(selectedFile: SelectedFile) {
        val existingFileName = getItems().toMutableList()
        existingFileName.remove(selectedFile)
        saveInModel(existingFileName)
    }

override fun deleteChangedFileItem(uri: String, newFileName: String) {
        val existingFileName = getItems().toMutableList()
        val oldFileName = existingFileName.find { it.longTermPath ==  uri}
        existingFileName.remove(oldFileName)
        if (oldFileName != null) {
            oldFileName.filePath = newFileName
            existingFileName.add(0, oldFileName)
        }
        saveInModel(existingFileName)
    }


    companion object {
        private const val PREF_KEY_NAME = "1"
    }
}
