package com.example.revsb_11.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.revsb_11.contracts.AddFileCommentsContract

class AddFileCommentsViewModel : ViewModel(), AddFileCommentsContract.ViewModel {
    private val _originalFile = MutableLiveData<String>()
    private val _fileImage = MutableLiveData<Bitmap>()
    private val _fileComment = MutableLiveData<String>()
    private val _changedComment = MutableLiveData<String>()
    private val _isButtonEnabled = MutableLiveData<Boolean>()
    private val _showConfirmationDialog = MutableLiveData<Boolean>()
    private val _saveChangedComment = MutableLiveData<Boolean>()

    val originalFile: LiveData<String>
        get() = _originalFile
    val fileImage: LiveData<Bitmap>
        get() = _fileImage
    val fileComment: LiveData<String>
        get() = _fileComment
    val isButtonEnabled: LiveData<Boolean>
        get() = _isButtonEnabled
    val showConfirmationDialog: LiveData<Boolean>
        get() = _showConfirmationDialog
    val saveChangedComment: LiveData<Boolean>
        get() = _saveChangedComment


    override fun processFirstArgument(fileName: String) {
        _originalFile.value = fileName
    }

    override fun processSecondArgument(fileComment: String) {
        _fileComment.value = fileComment
    }

    override fun saveFileImage(fileImage: Bitmap) {
        _fileImage.value = fileImage
    }

    override fun onTextHasBeenChanged(changedComment: String) {
        if (changedComment != _fileComment.value) {
            _isButtonEnabled.value = true
            _changedComment.value = changedComment
        } else {
            _isButtonEnabled.value = false
        }
    }

    override fun onSaveButtonClicked() {
        _showConfirmationDialog.value = true
    }

    override fun onConsentSaveButtonClicked() {
        _saveChangedComment.value = true
    }


}