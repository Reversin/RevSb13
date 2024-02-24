package com.revsb_11.viewmodels

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revsb_11.models.dataclasses.SelectedFile
import com.revsb_11.utils.ExtractFileDetails
import com.revsb_11.models.SelectedFilesModel
import com.revsb_11.models.dataclasses.SelectedFilesUIState
import com.revsb_11.utils.Event

class SelectedFilesViewModel(
    private val model: SelectedFilesModel,
    private val extractFileDetails: ExtractFileDetails
) : ViewModel() {

    private val _selectedFilesUIState = MutableLiveData<SelectedFilesUIState?>()
    val selectedFilesUIStateLiveData: MutableLiveData<SelectedFilesUIState?>
        get() = _selectedFilesUIState

    fun onScreenOpened() {
        _selectedFilesUIState.value = SelectedFilesUIState(
            savedSelectedFilesList = model.getSelectedFiles(),
            onFindFileButtonClicked = null,
            selectedFile = null
        )
    }

    fun onFindFileButtonClicked() {
        val newState = _selectedFilesUIState.value?.copy(onFindFileButtonClicked = Event(Unit))
        _selectedFilesUIState.value = newState
    }

    fun fileHasBeenSelected(uri: Uri) {
        uri.let { selectedUri ->
            val longTermPath = extractFileDetails.grantUriPermissions(selectedUri)
            val filePath = selectedUri.path
            val fileSize = filePath.let {
                extractFileDetails.getFileSize(
                    longTermPath
                )
            }
            model.saveSelectedFile(
                filePath,
                fileSize,
                longTermPath.toString(),
                ""
            )
        }

        _selectedFilesUIState.value =
            _selectedFilesUIState.value?.copy(savedSelectedFilesList = model.getSelectedFiles())
    }

    fun onSelectedFileClicked(selectedFile: SelectedFile) {
        _selectedFilesUIState.value = _selectedFilesUIState.value?.copy(selectedFile = selectedFile)
    }

    fun onSwipeDeleteItem(selectedFile: SelectedFile) {
        model.deleteSelectedFile(selectedFile)
        _selectedFilesUIState.value =
            _selectedFilesUIState.value?.copy(savedSelectedFilesList = model.getSelectedFiles())
    }

}

