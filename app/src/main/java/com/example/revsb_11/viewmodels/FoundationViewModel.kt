package com.example.revsb_11.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FoundationViewModel: ViewModel() {
    private val _confirmationBeforeReturning = MutableLiveData<Boolean>()
    private val _onNavigateUpArrowClicked = MutableLiveData<Boolean>()
    private val _localizationIndex = MutableLiveData<Int>()

    val onNavigateUpArrowClickedLiveData: LiveData<Boolean>
            get() = _onNavigateUpArrowClicked
    val confirmationBeforeReturningLiveData: LiveData<Boolean>
        get() = _confirmationBeforeReturning
    val localizationIndexLiveData: LiveData<Int>
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