package com.example.revsb_11.Contracts

import android.content.ContentResolver
import android.net.Uri
import com.example.revsb_11.Data.Item


interface FirstFragmentContract {
    interface View {
        fun openFileSelector()
        fun setFileNameTitle(item: Item)
        fun recoveryFileNames(items: List<Item>)
    }
    interface Presenter {
        fun onScreenOpened()
        fun onFindFIleButtonClicked()
        fun onFileNameSelected(item: Item)
        fun uriPassedContext(path:  ContentResolver?, selectedUri: Uri)

    }
    interface Model {
        fun saveItems(items: List<Item>)
        fun saveFileName(item: Item)
        fun recoveryFileNames(): List<Item>
    }
    interface Data{
        fun recyclePath(path:  ContentResolver?, selectedUri: Uri): String?
    }
}
