package com.example.revsb_11.presenters

import com.example.revsb_11.contracts.FirstFragmentContract
import com.example.revsb_11.data.Data


class FirstFragmentPresenter(
    private val view: FirstFragmentContract.View,
    private val model: FirstFragmentContract.Model,
) : FirstFragmentContract.Presenter {
    
    
    override fun onFindFIleButtonClicked() =
        view.openFileSelector()
    
    override fun onFileNameSelected(data: Data) {
        model.saveItem(data)
        val itemsList = model.getItems()
        view.setFileNameTitle(itemsList)
    }
    
    override fun modelInitialized() {
        val itemsList = model.getItems()
        view.initAdapterRecycleView(itemsList)
    }
    
    override fun onItemClicked(data: Data) {
        view.changeFragment(data)
    }
//    override fun setSecondPresenter(secondFragmentPresenter: SecondFragmentPresenter) {
//        this.secondFragmentPresenter = secondFragmentPresenter
//    }
}




