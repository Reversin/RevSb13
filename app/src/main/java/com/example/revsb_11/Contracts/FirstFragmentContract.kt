package com.example.revsb_11.Contracts

import android.content.ContentResolver
import android.net.Uri


interface FirstFragmentContract {
    interface View {
        fun openFileSelector()
        fun setFileNameTitle(filePath: String)
    }
    interface Presenter {
        fun onScreenOpened()
        fun onFindFIleButtonClicked()
        fun onFileNameSelected(text: String)
        fun uriPassedContext(path:  ContentResolver?, selectedUri: Uri)

    }
    interface Model {
        fun saveName(text: String)
        fun getFileName(): String?
    }
    interface Data{
        fun recyclePath(path:  ContentResolver?, selectedUri: Uri): String?
    }
}
