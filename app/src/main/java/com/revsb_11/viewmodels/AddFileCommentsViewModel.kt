package com.revsb_11.viewmodels


import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revsb_11.models.dataclasses.NewFileComment
import com.revsb_11.models.dataclasses.ScreenState
import com.revsb_11.models.SelectedFilesModel
import com.revsb_11.models.dataclasses.AddFileCommentsUIState
import com.revsb_11.models.dataclasses.SelectedFile
import com.revsb_11.repository.DriveRepository
import com.revsb_11.utils.ExtractFileDetails
import com.revsb_11.views.composeScreens.effects.AddCommentScreenEffect
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddFileCommentsViewModel(
    val extractFileDetails: ExtractFileDetails,
    val driveRepository: DriveRepository,
    val model: SelectedFilesModel,
) : ViewModel() {

    val screenState = ScreenState()
    private val fileImageType = "image/"

    private val _effect = MutableStateFlow<AddCommentScreenEffect?>(null)
    val effect = _effect.asStateFlow()

    private val _file = MutableLiveData<SelectedFile>()
    val fileLiveData: LiveData<SelectedFile>
        get() = _file

    private val _addFileCommentsUIState = MutableLiveData<AddFileCommentsUIState?>()
    val addFileCommentsUIStateLiveData: MutableLiveData<AddFileCommentsUIState?>
        get() = _addFileCommentsUIState

    private val _bitmap = MutableLiveData<Bitmap?>(null)
    val bitmapLiveData: MutableLiveData<Bitmap?>
        get() = _bitmap

    private val _errorTimeoutEvent = MutableLiveData<Boolean>()
    val errorTimeoutEventLiveData: LiveData<Boolean> = _errorTimeoutEvent

    private val _showConfirmBackDialog = MutableLiveData<Boolean>(false)
    val showConfirmBackDialogLiveData: LiveData<Boolean> = _showConfirmBackDialog


    fun onScreenOpened() {
        driveRepository.restoreGoogleAccount()

        val originalFile = model.getTransferSelectedFile()

        if (originalFile != null) {
            _addFileCommentsUIState.value =
                AddFileCommentsUIState(selectedFile = originalFile)
            loadImage()
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

    fun onTextHasBeenChanged(changedComment: String) {
        if (changedComment != _addFileCommentsUIState.value?.selectedFile?.fileComments) {
            _addFileCommentsUIState.value =
                _addFileCommentsUIState.value?.copy(
                    isSavingChangesButtonEnabled = true,
                    changedComment = changedComment
                )
        } else {
            _addFileCommentsUIState.value =
                _addFileCommentsUIState.value?.copy(isSavingChangesButtonEnabled = false)
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

    fun loadImage() {
        viewModelScope.launch {
            try {
                _bitmap.value = _addFileCommentsUIState.value?.selectedFile?.fileId?.let { fileId ->
                    driveRepository.loadImage(
                        fileId
                    )
                }
                // обработка успешной загрузки
            } catch (e: Exception) {
                _errorTimeoutEvent.postValue(true)
            }
        }
    }

    fun onBackClick(confirmed: Boolean = false) {
        if (_addFileCommentsUIState.value?.isSavingChangesButtonEnabled == true && !confirmed) {
            _showConfirmBackDialog.value = true
        } else {
            triggerEffect(AddCommentScreenEffect.BackToPreviousScreen)
        }
    }

    fun triggerEffect(newEffect: AddCommentScreenEffect) {
        _effect.value = newEffect
    }

    fun dismissDialog(dialogType: String) {
        when (dialogType) {
            TIMEOUT_DIALOG_TYPE -> _errorTimeoutEvent.value = false
            CONFIRM_BACK_DIALOG_TYPE -> _showConfirmBackDialog.value = false
        }
    }

    fun resetEffect() {
        _effect.value = null
        _bitmap.value = null
        onCleared()
    }


    companion object {

        const val TIMEOUT_DIALOG_TYPE = "timeout_dialog_type"
        const val CONFIRM_BACK_DIALOG_TYPE = "confirm_back_dialog_type"
    }
}