package com.example.revsb_11.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.revsb_11.dataclasses.SelectedFile
import com.example.revsb_11.utils.WorkingWithFiles
import com.example.revsb_11.models.SelectedFilesModel
import com.example.revsb_11.utils.Event

class SelectedFilesViewModel(
    private val model: SelectedFilesModel,
    private val workingWithFiles: WorkingWithFiles
) : ViewModel() {

    private val _savedSelectedFilesList = MutableLiveData<List<SelectedFile>>()
    val savedSelectedFilesListLiveData: LiveData<List<SelectedFile>>
        get() = _savedSelectedFilesList

    private val _selectedFilesList = MutableLiveData<List<SelectedFile>>()
    val selectedFilesListLiveData: LiveData<List<SelectedFile>>
        get() = _selectedFilesList

    private val _onFindFileButtonClicked = MutableLiveData<Event<Unit>>()
    val onFindFileButtonClickedLiveData: LiveData<Event<Unit>>
        get() = _onFindFileButtonClicked

    private val _selectedFile = MutableLiveData<SelectedFile>()
    val selectedFileLiveData: LiveData<SelectedFile>
        get() = _selectedFile

    fun onScreenOpened() {
        _savedSelectedFilesList.value = model.getSelectedFiles()
    }

    fun getSelectedFilesList() {
        _selectedFilesList.value = model.getSelectedFiles()
    }

    fun onFindFileButtonClicked() {
        _onFindFileButtonClicked.value = Event(Unit)
    }

    fun fileHasBeenSelected(uri: Uri) {
        uri.let { selectedUri ->
            val longTermPath = workingWithFiles.grantUriPermissions(selectedUri)
            val filePath = selectedUri.path
            val fileSize = filePath?.let {
                workingWithFiles.filePathHandlingSize(
                    longTermPath
                )
            }
            model.saveSelectedFile(filePath, fileSize, longTermPath.toString(), "")
        }

        _selectedFilesList.value = model.getSelectedFiles()
    }

    fun onSelectedFileClicked(selectedFile: SelectedFile) {
        _selectedFile.value = selectedFile
    }

    fun onSwipeDeleteItem(selectedFile: SelectedFile) {
        model.deleteSelectedFile(selectedFile)
    }

    fun fileCommentHasChanged(originalFile: String, newFileName: String) {
        model.deleteChangedFile(originalFile, newFileName)
        _selectedFilesList.value = model.getSelectedFiles()
    }
}

