package com.example.revsb_11.Presenters

import com.example.revsb_11.Contracts.FirstFragmentContract


class FirstFragmentPresenter(
    private val view: FirstFragmentContract.View,
    private val model: FirstFragmentContract.Model
) : FirstFragmentContract.Presenter {


    override fun onBlaBlaButtonClicked() {
        view.openFileSelector()
    }

    override fun onFileNameSelected(text: String) {
        view.setFileNameTitle(text)
        model.saveName(text)
    }


    override fun onScreenOpened() {
        val lastFileName = model.getFileName()
        if (lastFileName != null) {
            view.setFileNameTitle(lastFileName)
        }
    }
}

//TODO: убрать
//class MainActivityPresenter(private val view: FirstFragmentContract.View) : FirstFragmentContract.MainActivityPresenter{
//
//    override fun onOptionLangSelected(langKey: String) {
//        view.setLang(langKey)
//    }
//
//
//}


