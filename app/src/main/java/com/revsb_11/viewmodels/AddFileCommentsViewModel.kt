package com.revsb_11.viewmodels


import androidx.lifecycle.ViewModel
import com.revsb_11.models.dataclasses.NewFileComment
import com.revsb_11.models.dataclasses.ScreenState
import com.revsb_11.mappers.FileIconMapper
import com.revsb_11.utils.ExtractFileDetails

class AddFileCommentsViewModel(private val extractFileDetails: ExtractFileDetails) : ViewModel() {
    val screenState = ScreenState()
    private val fileImageType = "image/"


    fun initViewModel(originalFileUri: String, fileComment: String) {
        screenState._originalFileUri.value = originalFileUri
        extractFileDetails.getFileFormatFromUri(originalFileUri)
        val fileType = extractFileDetails.getFileFormatFromUri(originalFileUri)
        if (fileType?.startsWith(fileImageType) == true) {
            screenState._fileImage.value =
                extractFileDetails.getBitmapImageFromUri(originalFileUri)
        } else {
            screenState._fileIconResources.value = FileIconMapper().getIconResourceId(fileType)
        }
        screenState._fileName.value = extractFileDetails.getFileNameFromUri(originalFileUri)
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
        screenState._returnToPreviousScreen.value =
            screenState._originalFileUri.value?.let { originalFileUri ->
                screenState._changedComment.value?.let { changedComment ->
                    NewFileComment(
                        originalFileUri, changedComment
                    )
                }
            }
    }
}