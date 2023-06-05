package com.example.revsb_11.presenters

import com.example.revsb_11.contracts.FoundationContract

class FoundationPresenter(private val view: FoundationContract.View) : FoundationContract.Presenter{
    override fun onOptionLangSelected(langKey: Int) {
        view.setLang(langKey)
    }
}