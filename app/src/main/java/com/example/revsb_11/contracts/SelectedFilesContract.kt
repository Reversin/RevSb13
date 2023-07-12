package com.example.revsb_11.contracts


import android.content.ContentResolver
import android.net.Uri
import com.example.revsb_11.data.SelectedFile


interface SelectedFilesContract {
    interface View {
        fun openFileSelector()
        fun setFileNameTitle(itemsList: List<SelectedFile>)
        fun initAdapterRecycleView(itemsList: List<SelectedFile>)
        fun goToFragmentForChanges(selectedFile: SelectedFile)
    }

    interface Presenter {
        fun onFindFileButtonClicked()
        fun fileHasBeenSelected(selectedFile: Uri?, contentResolver: ContentResolver?)
        fun onScreenOpened()
        fun onItemClicked(selectedFile: SelectedFile)
        fun onSwipeDeleteItem(selectedFile: SelectedFile)
        fun fileNameHasChanged(originalFile: String, newFileName: String)
    }

    interface Model {
        fun saveInModel(data: List<SelectedFile>)
        fun saveItem(selectedFile: SelectedFile)
        fun getItems(): List<SelectedFile>
        fun deleteItem(selectedFile: SelectedFile)
        fun deleteChangedFileItem(uri: String, newFileName: String)
    }

}
