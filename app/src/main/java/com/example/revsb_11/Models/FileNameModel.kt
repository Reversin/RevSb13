package com.example.revsb_11.Models

import android.content.SharedPreferences
import com.example.revsb_11.Contracts.GetActionContract

class FileNameModel(private val prefs: SharedPreferences)
    : GetActionContract.Model {
    private var keyName: String = "key-1"

    override fun saveNameinModel(text: String) {
        prefs.edit()?.putString(keyName, text)?.apply()
    }

    override fun extractNameFromModel(): String? {
        return prefs.getString(keyName, "null")

    }

}
