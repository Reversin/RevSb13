package com.example.revsb_11.viewmodels

import android.graphics.Bitmap
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.revsb_11.data.NewFileComment
import com.example.revsb_11.data.WorkingWithFiles

class AddFileCommentsViewModel(private val workingWithFiles: WorkingWithFiles) : ViewModel() {
    private val _originalFileUri = MutableLiveData<String>()
    private val _fileNameLiveData = MutableLiveData<String>()
    private val _fileImage = MutableLiveData<Bitmap>()
    private val _fileComment = MutableLiveData<String>()
    private val _changedComment = MutableLiveData<String>()
    private val _isSavingChangesButtonEnabled = MutableLiveData<Boolean>()
    private val _showConfirmationDialog = MutableLiveData<Boolean>()
    private val _returnToPreviousScreen = MutableLiveData<NewFileComment>()

    val originalFileUriLiveData: LiveData<String>
        get() = _originalFileUri
    val fileNameLiveData: LiveData<String>
        get() = _fileNameLiveData
    val fileImageLiveData: LiveData<Bitmap>
        get() = _fileImage
    val fileCommentLiveData: LiveData<String>
        get() = _fileComment
    val isSavingChangesButtonLiveData: LiveData<Boolean>
        get() = _isSavingChangesButtonEnabled
    val showConfirmationDialogLiveData: LiveData<Boolean>
        get() = _showConfirmationDialog
    val returnToPreviousScreenLiveData: LiveData<NewFileComment>
        get() = _returnToPreviousScreen


    fun initViewModel(originalFileUri: String, fileComment: String) {
        _originalFileUri.value = originalFileUri
        _fileNameLiveData.value = workingWithFiles.getFileNameFromUri(originalFileUri.toUri())
        _fileImage.value = workingWithFiles.getBitmapImageFromUri(originalFileUri.toUri())
        _fileComment.value = fileComment
    }

    fun onTextHasBeenChanged(changedComment: String) {
        if (changedComment != _fileComment.value) {
            _isSavingChangesButtonEnabled.value = true
            _changedComment.value = changedComment
        } else {
            _isSavingChangesButtonEnabled.value = false
        }
    }

    fun onSaveButtonClicked() {
        _showConfirmationDialog.value = true
    }

    fun onConsentSaveButtonClicked() {
        _returnToPreviousScreen.value = _originalFileUri.value?.let {
            _changedComment.value?.let { it1 ->
                NewFileComment(
                    it, it1
                )
            }
        }
    }


}