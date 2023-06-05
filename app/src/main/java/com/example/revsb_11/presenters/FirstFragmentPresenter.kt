package com.example.revsb_11.presenters

import com.example.revsb_11.contracts.FirstFragmentContract
import com.example.revsb_11.data.Item


class FirstFragmentPresenter(
    private val view: FirstFragmentContract.View,
    private val model: FirstFragmentContract.Model,
) : FirstFragmentContract.Presenter {
    
    
    override fun onFindFIleButtonClicked() {
        view.openFileSelector()
    }
    
    override fun onFileNameSelected(item: Item) {
        view.setFileNameTitle(item)
    }
    
    override fun onScreenOpened() {
        val lastFileName = model.getItems()
        view.recoveryRV(lastFileName)
    }
}




