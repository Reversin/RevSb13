package com.example.revsb_11.viewmodels


import androidx.lifecycle.ViewModel
import com.example.revsb_11.dataclasses.NewFileComment
import com.example.revsb_11.dataclasses.ScreenState
import com.example.revsb_11.extensions.FileIconMapper
import com.example.revsb_11.utils.WorkingWithFiles

class AddFileCommentsViewModel(private val workingWithFiles: WorkingWithFiles) : ViewModel() {
    val screenState = ScreenState()
    private val fileImageType = "image/"


    fun initViewModel(originalFileUri: String, fileComment: String) {
        screenState._originalFileUri.value = originalFileUri
        workingWithFiles.getFileFormatFromUri(originalFileUri)
        val fileType = workingWithFiles.getFileFormatFromUri(originalFileUri)
        if (fileType?.startsWith(fileImageType) == true) {
            screenState._fileImage.value =
                workingWithFiles.getBitmapImageFromUri(originalFileUri)
        } else {
            screenState._fileIconResources.value = FileIconMapper().getIconResourceId(fileType)
        }
        screenState._fileName.value = workingWithFiles.getFileNameFromUri(originalFileUri)
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