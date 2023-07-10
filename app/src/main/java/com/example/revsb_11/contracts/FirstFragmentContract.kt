package com.example.revsb_11.contracts


import com.example.revsb_11.data.Data


interface FirstFragmentContract {
    interface View {
        fun openFileSelector()
        fun setFileNameTitle(itemsList: List<Data>)
        fun initAdapterRecycleView(itemsList: List<Data>)
        fun goToFragmentForChanges(data: Data)
        fun renameFile()

    }

    interface Presenter {
        fun onFindFIleButtonClicked()
        fun onFileNameSelected(data: Data)
        fun modelInitialized()
        fun onItemClicked(data: Data)
        fun swipeDeleteItem(data: Data)
        fun fileNameHasChanged()

    }

    interface Model {
        fun saveInModel(data: List<Data>)
        fun saveItem(data: Data)
        fun getItems(): List<Data>
        fun deleteItem(data: Data)
        fun deleteChangedFileItem(uri: String)
    }

}
