package com.example.revsb_11.presenters

import com.example.revsb_11.contracts.FirstFragmentContract
import com.example.revsb_11.data.Item


class FirstFragmentPresenter(
    private val view: FirstFragmentContract.View,
    private val model: FirstFragmentContract.Model,
) : FirstFragmentContract.Presenter {
    
    
    override fun onFindFIleButtonClicked() =
        view.openFileSelector()
    
    override fun onFileNameSelected(item: Item) {
        model.saveItem(item)
        val itemsList = model.getItems()
        view.setFileNameTitle(itemsList)
    }
    
    override fun modelInitialized() {
        val itemsList = model.getItems()
        view.initAdapterRecycleView(itemsList)
    }
    
    override fun onItemClicked(item: Item) {
        view.changeFragment(item)
    }
//    override fun setSecondPresenter(secondFragmentPresenter: SecondFragmentPresenter) {
//        this.secondFragmentPresenter = secondFragmentPresenter
//    }
}




