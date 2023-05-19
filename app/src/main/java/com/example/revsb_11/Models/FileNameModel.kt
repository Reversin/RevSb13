package com.example.revsb_11.Models

import android.content.SharedPreferences
import com.example.revsb_11.Contracts.FirstFragmentContract

import com.example.revsb_11.Views.FirstFragment

class FileNameModel(private val prefs: SharedPreferences)
    : FirstFragmentContract.Model{
    private var keyName: String = "1"

    override fun saveName(text: String) {
        prefs.edit()?.putString(keyName, text)?.apply()
    }

    override fun getFileName(): String? {
        return prefs.getString(keyName, null)

    }

}
