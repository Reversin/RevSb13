package com.example.revsb_11.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.revsb_11.utils.Event

class FoundationViewModel : ViewModel() {

    private val _onNavigateUpArrowClicked = MutableLiveData<Event<Unit>>()
    val onNavigateUpArrowClickedLiveData: LiveData<Event<Unit>>
        get() = _onNavigateUpArrowClicked

    private val _confirmationBeforeReturning = MutableLiveData<Event<Unit>>()
    val confirmationBeforeReturningLiveData: LiveData<Event<Unit>>
        get() = _confirmationBeforeReturning

    private val _localizationIndex = MutableLiveData<Int>()
    val localizationIndexLiveData: LiveData<Int>
        get() = _localizationIndex


    fun onNavigateUpArrowClicked() {
        _onNavigateUpArrowClicked.value = Event(Unit)
    }

    fun onBackToPreviousFragmentClicked() {
        _confirmationBeforeReturning.value = Event(Unit)
    }

    fun onOptionLangSelected(langKey: Int) {
        _localizationIndex.value = langKey
    }
}