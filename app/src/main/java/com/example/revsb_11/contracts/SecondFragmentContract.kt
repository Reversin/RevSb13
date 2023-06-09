package com.example.revsb_11.contracts

import android.content.ContentResolver
import android.net.Uri
import com.example.revsb_11.data.Item
import com.example.revsb_11.presenters.FirstFragmentPresenter
import com.example.revsb_11.presenters.SecondFragmentPresenter

interface SecondFragmentContract {
    
    interface View {
        fun setText(item: Item)
    }
    
    interface Presenter {
        fun getDataFromFirstFragment(item: Item)
//        fun setFirstPresenter(firstFragmentPresenter: FirstFragmentPresenter)
    }
    
    interface Model {
    }
    
    interface Data {
    }
}
