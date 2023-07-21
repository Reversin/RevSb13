package com.example.revsb_11.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.crypto.KeyAgreement

class FoundationViewModel: ViewModel() {
    private val _confirmationBeforeReturning = MutableLiveData<Boolean>()
    private val _onNavigateUpArrowClicked = MutableLiveData<Boolean>()
    private val _localizationIndex = MutableLiveData<Int>()

    val onNavigateUpArrowClicked: LiveData<Boolean>
            get() = _onNavigateUpArrowClicked
    val confirmationBeforeReturning: LiveData<Boolean>
        get() = _confirmationBeforeReturning
    val localizationIndex: LiveData<Int>
        get() = _localizationIndex


    fun onNavigateUpArrowClicked(){
        _onNavigateUpArrowClicked.value = true
    }

    fun onBackToPreviousFragmentClicked(agreement: Boolean){
        _confirmationBeforeReturning.value = agreement
    }
    fun onOptionLangSelected(langKey: Int) {
        _localizationIndex.value = langKey
    }

}