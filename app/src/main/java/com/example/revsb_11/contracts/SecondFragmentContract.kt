package com.example.revsb_11.contracts

interface SecondFragmentContract {
    
    interface View {
        fun setText(data: com.example.revsb_11.data.Data)
    }
    
    interface Presenter {
        fun getDataFromFirstFragment(data: com.example.revsb_11.data.Data)
//        fun setFirstPresenter(firstFragmentPresenter: FirstFragmentPresenter)
    }
    
    interface Model {
    }
    
    interface Data {
    }
}
