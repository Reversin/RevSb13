package com.example.revsb_11.viewmodels

import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.example.revsb_11.dataclasses.NewFileComment
import com.example.revsb_11.dataclasses.ScreenState
import com.example.revsb_11.extensions.WorkingWithFiles

class AddFileCommentsViewModel(private val workingWithFiles: WorkingWithFiles) : ViewModel() {
    val screenState = ScreenState()

    fun initViewModel(originalFileUri: String, fileComment: String) {
        screenState._originalFileUri.value = originalFileUri
        screenState._fileName.value = workingWithFiles.getFileNameFromUri(originalFileUri.toUri())
        screenState._fileImage.value =
            workingWithFiles.getBitmapImageFromUri(originalFileUri.toUri())
        screenState._fileComment.value = fileComment
    }

    fun onTextHasBeenChanged(changedComment: String) {
        if (changedComment != screenState.fileCommentLiveData.value) {
            screenState._isSavingChangesButtonEnabled.value = true
            screenState._changedComment.value = changedComment
        } else {
            screenState._isSavingChangesButtonEnabled.value = false
        }
    }

    fun onSaveButtonClicked() {
        screenState._showConfirmationDialog.value = true
    }

    fun onConsentSaveButtonClicked() {
        screenState._returnToPreviousScreen.value = screenState._originalFileUri.value?.let {
            screenState._changedComment.value?.let { it1 ->
                NewFileComment(
                    it, it1
                )
            }
        }
    }
}