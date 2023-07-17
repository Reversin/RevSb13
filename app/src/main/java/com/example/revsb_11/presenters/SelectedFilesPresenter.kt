package com.example.revsb_11.presenters

import android.content.ContentResolver
import android.net.Uri
import com.example.revsb_11.contracts.SelectedFilesContract
import com.example.revsb_11.data.SelectedFile
import com.example.revsb_11.data.WorkingWithFiles


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
        model.saveItem(filePath, fileSize, longTermPath, fileComments)
        val itemsList = model.getItems()
        view.updateFileCommentsList(itemsList)
    }

    override fun onScreenOpened() {
        val itemsList = model.getItems()
        view.initAdapterRecycleView(itemsList)
    }

    override fun onItemClicked(selectedFile: SelectedFile) {
        view.goToFragmentForChanges(selectedFile)
    }

    override fun onSwipeDeleteItem(selectedFile: SelectedFile) {
        model.deleteItem(selectedFile)
    }

    override fun fileCommentHasChanged(originalFile: String, newFileName: String) {
        model.deleteChangedFileItem(originalFile, newFileName)
        val itemsList = model.getItems()
        view.updateFileCommentsList(itemsList)
    }
}




