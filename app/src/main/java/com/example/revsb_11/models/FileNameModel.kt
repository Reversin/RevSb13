package com.example.revsb_11.models

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.revsb_11.contracts.FirstFragmentContract
import com.example.revsb_11.data.Item
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FileNameModel(private val prefs: SharedPreferences) : FirstFragmentContract.Model {
    
    private val gson = Gson()
    override fun saveInModel(items: List<Item>) {
        val json = gson.toJson(items)
        prefs.edit { putString(PREF_KEY_NAME, json) }
    }

    override fun saveItem(item: Item) {
        val existingItems = getItems().toMutableList()
        existingItems.removeAll { it == item}
        existingItems.add(0, item)
        saveInModel(existingItems)
    }
    

    override fun getItems(): List<Item> {
        val json = prefs.getString(PREF_KEY_NAME, null)
        val gson = Gson()
        return gson.fromJson(json, object : TypeToken<List<Item>>() {}.type) ?: mutableListOf()
    }

    override fun removeItem(position: Int) {
        val existingItems = getItems().toMutableList()
        existingItems.removeAt(position)
        saveInModel(existingItems)
    }


    companion object {

        private const val PREF_KEY_NAME = "2"
    }
}
