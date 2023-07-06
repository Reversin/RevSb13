package com.example.revsb_11.contracts

import com.example.revsb_11.views.SecondFragmentArgs

interface SecondFragmentContract {
    
    interface View {
        fun setText(dataFragmentArgument: SecondFragmentArgs)
        fun backToThePreviousFragment(): Boolean
        fun onTextChanged()
    }
    
    interface Presenter {
        fun secondFragmentInitialized(dataFragmentArgument: SecondFragmentArgs)
        fun onBackArrowClicked(): Boolean
    }
    
    interface Model {
    }
    
    interface Data {
    }
}
