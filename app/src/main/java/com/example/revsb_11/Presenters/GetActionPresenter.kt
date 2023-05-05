package com.example.revsb_11.Presenters

import com.example.revsb_11.Contracts.GetActionContract


class FirstFragmentPresenter(
    private val view: GetActionContract.View,
    private val model: GetActionContract.Model
) : GetActionContract.Presenter {
    //private val files: GetActionContract.Files): GetActionContract.Presenter {//FileNameModel) : GetActionContract.Presenter {


    override fun buttonFileDialogReaction() {
        view.takeFileNameDialog()
    }

    override fun processFileName(text: String) {
        view.editTVFileName(text, true)
        model.saveNameinModel(text)
    }

    override fun changeLocalization(langKey: String) {
        view.setLocale(langKey)
    }


    override fun recoveryLastFileName() {
        val lastFileName = model.extractNameFromModel()
        if (lastFileName != null) {
            view.editTVFileName(lastFileName, false)
        }
    }
}


