package com.example.revsb_11.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    var _editTextChanged = MutableLiveData<Boolean>()

    val editTextChangedLiveData: LiveData<Boolean>
        get() = _editTextChanged

    fun onEditTextChanged(changed: Boolean){
        _editTextChanged.value = changed
    }
}