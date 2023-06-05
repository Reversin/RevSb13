package com.example.revsb_11.contracts

import android.content.ContentResolver
import android.net.Uri
import com.example.revsb_11.data.Item


interface FirstFragmentContract {
    interface View {
        fun openFileSelector()
        fun setFileNameTitle(item: Item)
        fun recoveryRV(items: List<Item>)
    }
    
    interface Presenter {
        fun onScreenOpened()
        fun onFindFIleButtonClicked()
        fun onFileNameSelected(item: Item)
    }
    
    interface Model {
        fun saveInModel(items: List<Item>)
        fun saveItems(items: List<Item>)
        fun getItems(): List<Item>
        fun removeItem(position: Int)
    }
    
    interface Data {
        fun recyclePath(path: ContentResolver?, selectedUri: Uri): String?
    }
}
