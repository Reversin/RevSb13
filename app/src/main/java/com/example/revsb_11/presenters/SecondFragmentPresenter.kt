package com.example.revsb_11.presenters

import com.example.revsb_11.contracts.SecondFragmentContract
import com.example.revsb_11.views.SecondFragmentArgs


class SecondFragmentPresenter(
    private val view: SecondFragmentContract.View
) : SecondFragmentContract.Presenter {

    override fun secondFragmentInitialized() =
        view.setText()

    override fun onBackArrowClicked() : Boolean =
        view.backToThePreviousFragment()

    override fun onSaveButtonClicked() {
        view.showAlertDialog()
    }

    override fun onConsentSaveButton() {
        view.backToThePreviousFragmentWithChanges()
    }
}