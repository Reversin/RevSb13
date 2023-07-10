package com.example.revsb_11.models


import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.revsb_11.contracts.FirstFragmentContract
import com.example.revsb_11.data.Data
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class FileNameModel(private val prefs: SharedPreferences) : FirstFragmentContract.Model {

    private val gson = Gson()
    override fun saveInModel(data: List<Data>) {
        val json = gson.toJson(data)
        prefs.edit { putString(PREF_KEY_NAME, json) }
    }

    override fun saveItem(data: Data) {
        val existingItems = getItems().toMutableList()
        existingItems.removeAll { (it.filePath)?.equals(data.filePath) ?: false }
        existingItems.add(0, data)
        saveInModel(existingItems)
    }


    override fun getItems(): List<Data> {
        val json = prefs.getString(PREF_KEY_NAME, null)
        val gson = Gson()
        return gson.fromJson(json, object : TypeToken<List<Data>>() {}.type) ?: mutableListOf()
    }

    override fun deleteItem(data: Data) {
        val existingItems = getItems().toMutableList()
        existingItems.remove(data)
        saveInModel(existingItems)
    }

    override fun deleteChangedFileItem(uri: String) {
        val existingItems = getItems().toMutableList()
//        existingItems.removeAll { (it.filePath)?.equals(uri) ?: false }
//        existingItems.add(0, data)
//        saveInModel(existingItems)
    }


    companion object {
        private const val PREF_KEY_NAME = "1"
    }
}
