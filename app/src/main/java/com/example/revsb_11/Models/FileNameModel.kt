package com.example.revsb_11.Models

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.revsb_11.Contracts.FirstFragmentContract

class FileNameModel(private val prefs: SharedPreferences?)
    : FirstFragmentContract.Model{
    private val keyName: String = "1"

    override fun saveName(text: String)  {
        prefs?.edit { putString(keyName, text) }
    }
    override fun getFileName(): String? =
        prefs?.getString(keyName, null)

}
