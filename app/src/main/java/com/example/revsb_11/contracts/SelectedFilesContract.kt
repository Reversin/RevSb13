package com.example.revsb_11.contracts


import android.content.ContentResolver
import android.net.Uri
import com.example.revsb_11.data.SelectedFile


interface SelectedFilesContract {
    interface View {
        fun openFileSelector()
        fun updateFileCommentsList(selectedFilesList: List<SelectedFile>)
        fun initAdapterRecycleView(selectedFilesList: List<SelectedFile>)
        fun goToFragmentForChanges(selectedFile: SelectedFile)
    }

    interface Presenter {
        fun onFindFileButtonClicked()
        fun fileHasBeenSelected(filePath: String?, fileSize: String?, longTermPath: String, fileComments: String)
        fun onScreenOpened()
        fun onItemClicked(selectedFile: SelectedFile)
        fun onSwipeDeleteItem(selectedFile: SelectedFile)
        fun fileCommentHasChanged(originalFile: String, newFileName: String)
    }

    interface Model {
        fun saveInModel(data: List<SelectedFile>)
        fun saveItem(filePath: String?, fileSize: String?, longTermPath: String, fileComments: String)
        fun getItems(): List<SelectedFile>
        fun deleteItem(selectedFile: SelectedFile)
        fun deleteChangedFileItem(uri: String, newFileComment: String)
    }

}
