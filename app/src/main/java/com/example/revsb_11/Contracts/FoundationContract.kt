package com.example.revsb_11.Contracts

interface FoundationContract {
    interface View {
        fun setLang(langKey: String)
    }
    interface Presenter {
        fun onOptionLangSelected(langKey: String)
    }

}