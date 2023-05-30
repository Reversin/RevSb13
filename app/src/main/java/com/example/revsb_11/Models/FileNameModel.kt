package com.example.revsb_11.Models

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.revsb_11.Contracts.FirstFragmentContract
import com.example.revsb_11.Data.Item
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FileNameModel(private val prefs: SharedPreferences?)
    : FirstFragmentContract.Model{
    private val keyName: String = "2"

    override fun saveItems(items: List<Item>)  {
        val gson = Gson()
        val json = gson.toJson(items)
        prefs?.edit { putString(keyName, json) }
    }

    override fun saveFileName(item: Item) {
        val items = recoveryFileNames().toMutableList()
        items.add(item)
        saveItems(items)
    }
    override fun recoveryFileNames(): List<Item> {
        val json = prefs?.getString(keyName, null)
        val gson = Gson()
        return if (json != null){
            gson.fromJson(json, object : TypeToken<List<Item>>() {}.type)
        } else {
            mutableListOf()
        }
    }

}
