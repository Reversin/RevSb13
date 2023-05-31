package com.example.revsb_11.Presenters

import com.example.revsb_11.Contracts.FoundationContract

class FoundationPresenter(private val view: FoundationContract.View) : FoundationContract.Presenter{
    override fun onOptionLangSelected(langKey: Int) {
        view.setLang(langKey)
    }
}