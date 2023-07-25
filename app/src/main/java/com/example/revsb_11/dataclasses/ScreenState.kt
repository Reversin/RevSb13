package com.example.revsb_11.dataclasses

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

data class ScreenState(
    val _originalFileUri: MutableLiveData<String> = MutableLiveData(),
    val _fileName: MutableLiveData<String> = MutableLiveData(),
    val _fileImage: MutableLiveData<Bitmap> = MutableLiveData(),
    val _fileComment: MutableLiveData<String> = MutableLiveData(),
    val _changedComment: MutableLiveData<String> = MutableLiveData(),
    val _isSavingChangesButtonEnabled: MutableLiveData<Boolean> = MutableLiveData(),
    val _showConfirmationDialog: MutableLiveData<Boolean> = MutableLiveData(),
    val _returnToPreviousScreen: MutableLiveData<NewFileComment> = MutableLiveData()
) : MediatorLiveData<ScreenState>() {
    val fileNameLiveData: LiveData<String>
        get() = _fileName
    val fileImageLiveData: LiveData<Bitmap>
        get() = _fileImage
    val fileCommentLiveData: LiveData<String>
        get() = _fileComment
    val _isSavingChangesButtonEnabledLiveData: LiveData<Boolean>
        get() = _isSavingChangesButtonEnabled
    val showConfirmationDialogLiveData: LiveData<Boolean>
        get() = _showConfirmationDialog
    val returnToPreviousScreenLiveData: LiveData<NewFileComment>
        get() = _returnToPreviousScreen
}
