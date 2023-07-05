package com.example.revsb_11.contracts

import android.content.ContentResolver
import android.net.Uri
import com.example.revsb_11.data.Data


interface FirstFragmentContract {
    interface View {
        fun openFileSelector()
        fun setFileNameTitle(itemsList: List<Data>)
        fun initAdapterRecycleView(itemsList: List<Data>)
        fun goToFragmentForChanges(data: Data)
        
    }
    
    interface Presenter {
        fun onFindFIleButtonClicked()
        fun onFileNameSelected(data: Data)
        fun modelInitialized()
        fun onItemClicked(data: Data)
//        fun setSecondPresenter(secondFragmentPresenter: SecondFragmentPresenter)
        fun swipeDeleteItem(data: Data)
    
    }
    
    interface Model {
        fun saveInModel(data: List<Data>)
        fun saveItem(data: Data)
        fun getItems(): List<Data>
        fun deleteItem(data: Data)
    }
    
}
