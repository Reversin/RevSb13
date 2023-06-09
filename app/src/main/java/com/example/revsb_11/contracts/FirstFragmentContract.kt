package com.example.revsb_11.contracts

import android.content.ContentResolver
import android.net.Uri
import com.example.revsb_11.data.Item
import com.example.revsb_11.presenters.SecondFragmentPresenter


interface FirstFragmentContract {
    interface View {
        fun openFileSelector()
        fun setFileNameTitle(itemsList: List<Item>)
        fun initAdapterRecycleView(itemsList: List<Item>)
        fun changeFragment(item: Item)
        
    }
    
    interface Presenter {
        fun onFindFIleButtonClicked()
        fun onFileNameSelected(item: Item)
        fun modelInitialized()
        fun onItemClicked(item: Item)
//        fun setSecondPresenter(secondFragmentPresenter: SecondFragmentPresenter)
    
    }
    
    interface Model {
        fun saveInModel(items: List<Item>)
        fun saveItem(item: Item)
        fun getItems(): List<Item>
        fun removeItem(position: Int)
    }
    
    interface Data {
        fun recyclePath(contentResolver: ContentResolver?, uri: Uri): String?
    }
}
