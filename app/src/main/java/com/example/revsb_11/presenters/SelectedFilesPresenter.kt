package com.example.revsb_11.presenters

import com.example.revsb_11.contracts.SelectedFilesContract
import com.example.revsb_11.data.SelectedFile


class SelectedFilesPresenter(
    private val view: SelectedFilesContract.View,
    private val model: SelectedFilesContract.Model,
) : SelectedFilesContract.Presenter {


    override fun onFindFileButtonClicked() =
        view.openFileSelector()

    override fun fileHasBeenSelected(
        filePath: String?,
        fileSize: String?,
        longTermPath: String,
        fileComments: String
    ) {
        model.saveSelectedFile(filePath, fileSize, longTermPath, fileComments)
        val selectedFilesList = model.getSelectedFiles()
        view.updateFileCommentsList(selectedFilesList)
    }

    override fun onScreenOpened() {
        val selectedFilesList = model.getSelectedFiles()
        view.initAdapterRecycleView(selectedFilesList)
    }

    override fun onItemClicked(selectedFile: SelectedFile) {
        view.goToFragmentForChanges(selectedFile)
    }

    override fun onSwipeDeleteItem(selectedFile: SelectedFile) {
        model.deleteSelectedFile(selectedFile)
    }

    override fun fileCommentHasChanged(originalFile: String, newFileName: String) {
        model.deleteChangedFile(originalFile, newFileName)
        val selectedFilesList = model.getSelectedFiles()
        view.updateFileCommentsList(selectedFilesList)
    }
}




