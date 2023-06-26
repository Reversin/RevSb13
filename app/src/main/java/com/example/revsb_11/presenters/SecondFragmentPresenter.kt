package com.example.revsb_11.presenters

import com.example.revsb_11.contracts.SecondFragmentContract
import com.example.revsb_11.data.Data

class SecondFragmentPresenter(
    private val view: SecondFragmentContract.View
) : SecondFragmentContract.Presenter {
    private lateinit var firstFragmentPresenter: FirstFragmentPresenter
    
//    override fun setFirstPresenter(firstFragmentPresenter: FirstFragmentPresenter) {
//        this.firstFragmentPresenter = firstFragmentPresenter
//    }

    override fun getDataFromFirstFragment(data: Data) {
        view.setText(data)
    }
    
}