package com.revsb_11.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.api.services.drive.model.File
import com.revsb_11.models.dataclasses.SelectedFile
import com.revsb_11.utils.ExtractFileDetails
import com.revsb_11.models.SelectedFilesModel
import com.revsb_11.models.dataclasses.SelectedFilesUIState
import com.revsb_11.repository.DriveRepository
import com.revsb_11.utils.Event
import kotlinx.coroutines.launch

class SelectedFilesViewModel(
    private val model: SelectedFilesModel,
    private val extractFileDetails: ExtractFileDetails,
    private val driveRepository: DriveRepository,
) : ViewModel() {

    private val _selectedFilesUIState = MutableLiveData<SelectedFilesUIState?>()
    val selectedFilesUIStateLiveData: MutableLiveData<SelectedFilesUIState?>
        get() = _selectedFilesUIState

    private val _driveFolders = MutableLiveData<List<String>>()
    val driveFoldersLiveData: LiveData<List<String>>
        get() = _driveFolders

    private val _loadError = MutableLiveData<String>()
    val loadError: LiveData<String> get() = _loadError

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _endOfList = MutableLiveData<Boolean>()
    val endOfList: LiveData<Boolean> get() = _endOfList

    private val _images = MutableLiveData<List<File>>()
    val images: LiveData<List<File>> get() = _images

    fun onScreenOpened() {
        _selectedFilesUIState.value = SelectedFilesUIState(
            savedSelectedFilesList = model.getSelectedFiles(),
            onFindFileButtonClicked = null,
            selectedFile = null
        )
        driveRepository.restoreGoogleAccount()
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

    fun setGoogleAccount(account: GoogleSignInAccount) {
        driveRepository.setGoogleAccount(account)
    }

    fun loadFolders() {
        viewModelScope.launch {
            _driveFolders.value = driveRepository.getFolders()
        }
    }

    fun loadImages() {
        viewModelScope.launch {

            if (_images.value.isNullOrEmpty()) {
                _isLoading.value = true
            }
            _loadError.value = ""

            try {
                val newImages = driveRepository.getImages()  // Загрузка изображений
                _images.value = _images.value.orEmpty() + newImages
                _endOfList.value =
                    newImages.isEmpty()  // Установите в true, если достигнут конец списка
            } catch (e: Exception) {
                _loadError.value = e.message ?: "Unknown error"
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun createFolder() {
        viewModelScope.launch {
            val folderList = driveRepository.createFolder()
        }
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

