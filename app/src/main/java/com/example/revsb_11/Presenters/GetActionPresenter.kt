package com.example.revsb_11.Presenters

import com.example.revsb_11.Contracts.GetActionContract


class FirstFragmentPresenter(
    private val view: GetActionContract.FirstFragmentView,
    private val model: GetActionContract.Model
) : GetActionContract.FirstFragmentPresenter {
    //private val files: GetActionContract.Files): GetActionContract.Presenter {//FileNameModel) : GetActionContract.Presenter {


    override fun onBlaBlaButtonClicked() {
        view.openFileSelector()
    }

    override fun onFileNameSelected(text: String) {
        view.setFileNameTitle(text)
        model.saveName(text)
    }

//    override fun onLanguageSelected(langKey: String) {
//        view.setLocale(langKey)
//    }


    override fun onScreenOpened() {
        val lastFileName = model.getFileName()
        if (lastFileName != null) {
            view.setFileNameTitle(lastFileName)
        }
    }
}

class MainActivityPresenter(private val view: GetActionContract.MainActivityView) : GetActionContract.MainActivityPresenter{

    override fun onOptionLangSelected(langKey: String) {
        view.setLang(langKey)
    }


}


