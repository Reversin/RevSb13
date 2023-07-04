package com.example.revsb_11.contracts

import com.example.revsb_11.views.SecondFragmentArgs

interface SecondFragmentContract {
    
    interface View {
        fun setText(dataFragmentArgument: SecondFragmentArgs)
    }
    
    interface Presenter {
        fun secondFragmentInitialized(dataFragmentArgument: SecondFragmentArgs)
//        fun setFirstPresenter(firstFragmentPresenter: FirstFragmentPresenter)
    }
    
    interface Model {
    }
    
    interface Data {
    }
}
