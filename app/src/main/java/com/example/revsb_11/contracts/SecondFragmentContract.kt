package com.example.revsb_11.contracts

interface SecondFragmentContract {

    interface View {
        fun setText()
        fun backToThePreviousFragment(): Boolean
        fun showAlertDialog()
        fun backToThePreviousFragmentWithChanges()

    }

    interface Presenter {
        fun onScreenOpened()
        fun onBackArrowClicked(): Boolean
        fun onSaveButtonClicked()
        fun onConsentSaveButtonClicked()
    }
}
