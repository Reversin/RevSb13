package com.revsb_11.viewmodels


import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revsb_11.models.dataclasses.NewFileComment
import com.revsb_11.models.dataclasses.ScreenState
import com.revsb_11.mappers.FileIconMapper
import com.revsb_11.models.SelectedFilesModel
import com.revsb_11.models.dataclasses.SelectedFile
import com.revsb_11.repository.DriveRepository
import com.revsb_11.utils.ExtractFileDetails
import com.revsb_11.utils.ExtractFileDetails.Companion.TRANSFER_FILE_COMMENT
import com.revsb_11.utils.ExtractFileDetails.Companion.TRANSFER_FILE_URI
import com.revsb_11.views.composeScreens.effects.AddCommentScreenEffect
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddFileCommentsViewModel(
    private val extractFileDetails: ExtractFileDetails,
    private val driveRepository: DriveRepository,
    private val model: SelectedFilesModel,
) : ViewModel() {

    val screenState = ScreenState()
    private val fileImageType = "image/"

    private val _effect = MutableStateFlow<AddCommentScreenEffect?>(null)
    val effect = _effect.asStateFlow()

    private val _file = MutableLiveData<SelectedFile>()
    val fileLiveData: LiveData<SelectedFile>
        get() = _file

    fun onScreenOpened() {
        viewModelScope.launch {
            val originalFileId = model.getTransferSelectedFile()

            if (originalFileId != null) {
                _file.value = driveRepository.getFileById(originalFileId)
//                screenState._originalFileUri.value = originalFileUri
//                extractFileDetails.getFileFormatFromUri(originalFileUri)
//                val fileType = extractFileDetails.getFileFormatFromUri(originalFileUri)
//                if (fileType?.startsWith(fileImageType) == true) {
//                    screenState._fileImage.value =
//                        extractFileDetails.getBitmapImageFromUri(originalFileUri)
//                } else {
//                    screenState._fileIconResources.value = FileIconMapper().getIconResourceId(fileType)
//                }
//                screenState._fileName.value = extractFileDetails.getFileNameFromUri(originalFileUri)
//                screenState._fileComment.value = fileComment
            }
        }
    }


    suspend fun getFileById(fileId: String) {
        val file = driveRepository.getFileById(fileId)
        if (file != null) {
            // Обработка полученного файла
        } else {
            // Обработка ошибки
        }
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

    fun triggerEffect(newEffect: AddCommentScreenEffect) {
        _effect.value = newEffect
    }

    fun resetEffect() {
        _effect.value = null
    }
}