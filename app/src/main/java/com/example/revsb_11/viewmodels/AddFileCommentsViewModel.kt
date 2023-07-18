package com.example.revsb_11.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment.Companion
import com.example.revsb_11.views.AddFileCommentsFragmentDirections

class AddFileCommentsViewModel : ViewModel() {
    private val _originalFile = MutableLiveData<String>()
    private val _fileComment = MutableLiveData<String>()
    private val _changedComment = MutableLiveData<String>()
    private val _isButtonEnabled = MutableLiveData<Boolean>()
    private val _showConfirmationDialog = MutableLiveData<Boolean>()
    private val _saveChangedComment = MutableLiveData<Boolean>()

    val originalFile: LiveData<String>
        get() = _originalFile
    val fileComment: LiveData<String>
        get() = _fileComment
    val isButtonEnabled: LiveData<Boolean>
        get() = _isButtonEnabled
    val showConfirmationDialog: LiveData<Boolean>
        get() = _showConfirmationDialog
    val saveChangedComment: LiveData<Boolean>
        get() = _saveChangedComment


    fun processFirstArgument(fileName: String) {
        _originalFile.value = fileName
    }

    fun processSecondArgument(fileComment: String) {
        _fileComment.value = fileComment
    }

    fun onTextHasBeenChanged(changedComment: String) {
        if (changedComment != _fileComment.value) {
            _isButtonEnabled.value = true
            _changedComment.value = changedComment
        } else {
            _isButtonEnabled.value = false
        }
    }

    fun onSaveButtonClicked() {
        _showConfirmationDialog.value = true
    }

    fun onConsentSaveButtonClicked() {
        _saveChangedComment.value = true
    }
}