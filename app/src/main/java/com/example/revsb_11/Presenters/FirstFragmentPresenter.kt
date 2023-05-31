package com.example.revsb_11.Presenters

import com.example.revsb_11.Contracts.FirstFragmentContract
import com.example.revsb_11.Data.Item


class FirstFragmentPresenter(
    private val view: FirstFragmentContract.View,
    private val model: FirstFragmentContract.Model,
) : FirstFragmentContract.Presenter {


    override fun onFindFIleButtonClicked() {
        view.openFileSelector()
    }

    override fun onFileNameSelected(item: Item) {
        view.setFileNameTitle(item)
        model.saveFileName(item)
    }


    override fun onScreenOpened() {
        val lastFileName = model.recoveryFileNames()
        view.recoveryRV(lastFileName)
    }
}




