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
    
    override fun fileHasBeenSelected(selectedFile: Uri?, contentResolver: ContentResolver?) {
        selectedFile?.let { uri ->
            val longFileUri = WorkingWithFiles().grantUriPermissions(contentResolver, uri)
            val filepath = uri.path
            val fileSize = filepath?.let {
                WorkingWithFiles().filePathHandlingSize(
                    contentResolver, longFileUri
                )
            }
            model.saveItem(SelectedFile(longFileUri.toString(), fileSize, longFileUri.toString()))
        }
        val itemsList = model.getItems()
        view.setFileNameTitle(itemsList)
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
    
    override fun fileNameHasChanged(originalFile: String, newFileName: String) {
        model.deleteChangedFileItem(originalFile, newFileName)
        val itemsList = model.getItems()
        view.setFileNameTitle(itemsList)
    }
}




