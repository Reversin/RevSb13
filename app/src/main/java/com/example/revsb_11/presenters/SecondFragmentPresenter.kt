package com.example.revsb_11.presenters

import com.example.revsb_11.contracts.SecondFragmentContract
import com.example.revsb_11.views.SecondFragmentArgs


class SecondFragmentPresenter(
    private val view: SecondFragmentContract.View
) : SecondFragmentContract.Presenter {
    private lateinit var firstFragmentPresenter: FirstFragmentPresenter

    override fun secondFragmentInitialized(dataFragmentArgument: SecondFragmentArgs) {
        view.setText(dataFragmentArgument)
    }
    
}