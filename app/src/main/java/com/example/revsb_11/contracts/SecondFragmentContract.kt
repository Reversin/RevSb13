package com.example.revsb_11.contracts

import com.example.revsb_11.views.SecondFragmentArgs

interface SecondFragmentContract {
    
    interface View {
        fun setText()
        fun backToThePreviousFragment(): Boolean
        fun showAlertDialog()
        fun backToThePreviousFragmentWithChanges()

    }
    
    interface Presenter {
        fun secondFragmentInitialized()
        fun onBackArrowClicked(): Boolean
        fun onSaveButtonClicked()
        fun onConsentSaveButton()
    }
    
    interface Model {
    }
    
    interface Data {
    }
}
